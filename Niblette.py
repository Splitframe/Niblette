import string
import sys
import os
import shlex
import struct
import atexit
import re
import threading
import logging

#import sqlite3 as sl
import time

import irc.bot
import irc.strings

from irc.client import ip_numstr_to_quad, ip_quad_to_numstr
from jaraco.stream import buffer
#from sqlitedao import SqliteDao, ColumnDict


bot = None
isWindows = False

# def create_title(dao: SqliteDao):
#     columns = ColumnDict()\
#         .add_column("id", "integer", "primary key")\
#         .add_column("name", "text", "not null") \
#         .add_column("search_terms", "text") \
#         .add_column("season", "text", "not null") \
#         .add_column("season_name", "text", "not null") \
#         .add_column("episode", "integer", "not null") \
#         .add_column("resolution", "integer", "not null") # 0 - Unknown, 1 - 480p, 2 - 720p, 3 - 1080p, 4 - 2160p
#
#     dao.create_table("title", columns)
#
# def create_episode(dao: SqliteDao):
#     columns = ColumnDict() \
#         .add_column("id", "integer", "primary key") \
#         .add_column("filename", "integer", "not null") \
#         .add_column("episode", "integer", "not null") \
#
#     dao.create_table("episode", columns)

class Niblette(irc.bot.SingleServerIRCBot):

    botPattern    = re.compile(r"(?<=MSG CR-HOLLAND\|NEW ).*$")
    showPattern   = re.compile(r"(?<=\])([^-]{3,}?)(?=\-)")
    seasonPattern = re.compile(r"S(\d+)")
    logging.basicConfig(level=os.environ.get("LOGLEVEL", "INFO"))

    def __init__(self, channel, nickname, server, port=6667):
        irc.bot.SingleServerIRCBot.__init__(self, [(server, port)], nickname, nickname)
        self.connection.buffer_class = buffer.LenientDecodingLineBuffer
        self.channel = channel
        self.downloader = self.dcc("raw")
        self.total_bytes = 0
        self.received_bytes = 0
        self.file = None
        self.dccTimer = None
        self.dccMaxTries = 100
        self.dccTries = 0
        self.log = logging.getLogger(__name__)
        self.queue = []

    # def init_database(self):
    #     self.db = sl.connect("niblette.db")
    #     self.dao: SqliteDao = SqliteDao.get_instance("niblette.db")
    #     create_title(self.dao)
    #     create_episode(self.dao)

    def on_nicknameinuse(self, connection, event):
        connection.nick(connection.get_nickname() + "_")

    def on_welcome(self, connection, event):
        connection.join(self.channel)

    def on_privmsg(self, connection, event):
        nickname = event.source.nick
        message: str = event.arguments[0]
        print(f"Whisper from {nickname}: {message}")
        match = Niblette.botPattern.findall(message.strip("'"))
        if(match):
            print(f"Regex Match, output: {match[0]}")
            connection.privmsg(nickname, f"Regex match output: {match[0]}")
            connection.privmsg("CR-HOLLAND|NEW", f"{match[0]}")

    def on_pubmsg(self, connection, event):

        nickname = event.source.nick
        message:str = event.arguments[0]  # Message
        print(f"Sender: {nickname}, Message: {message}")
        if (nickname == "CR-HOLLAND|NEW"):
            if ("(1080p)" in message):
                print(f"Relevant Message from Source Bot: {message}.")
                match = Niblette.botPattern.findall(message.strip("'"))
                if(match):
                    print("Conditions met, requesting download.")
                    connection.privmsg("CR-HOLLAND|NEW", f"{match[0]}")
        return

    def do_command(self, event, cmd):
        nick = event.source.nick
        connection = self.connection

        if (cmd == "help"):
            connection.notice(nick, "This is a passive leech bot and has no active commands.")
            connection.notice(nick, "If you are the channel admin and have a problem just kick the bot.")
        else:
            connection.notice(nick, "Not understood: " + cmd)
            connection.notice(nick, "Send 'help' for more information")

    def on_ctcp(self, connection, event):
        print(event)
        if (event.arguments[0] == "VERSION"):
            connection.ctcp_reply(event.source, "VERSION Niblette 0.1a")
            return

        payload = event.arguments[1]
        parts = shlex.split(payload)
        command, filename, peer_address, peer_port, size = parts

        if (command != "SEND"):
            return

        showName, season = determineStructure(filename)

        if (isWindows):
            fullpath   = filename
        else:
            showPath   = f"/usr/download/Anime/{showName}"
            seasonPath = f"/usr/download/Anime/{showName}/{season}"
            fullpath   = f"/usr/download/Anime/{showName}/{season}/{filename}"
            if (not os.path.exists(showPath)):
                os.mkdir(showPath)
            if (not os.path.exists(seasonPath)):
                os.mkdir(seasonPath)

        print("Receiving a file. Potential location: ", fullpath)
        if (os.path.exists(fullpath)):
            print("A file named", fullpath, "already exists. Refusing to save it.")
            return
        if (self.downloader.connected == True):
            self.queue.append((connection, event))
            return
        print("Downloading ", filename)
        self.total_bytes = int(size)
        print(f"Size: {self.total_bytes}")
        self.received_bytes = 0
        if (self.file is not None):
            self.file.close()
        self.file = open(fullpath, "wb")
        peer_address = irc.client.ip_numstr_to_quad(peer_address)
        peer_port = int(peer_port)
        self.downloader = self.dcc("raw")
        self.downloader = self.downloader.connect(peer_address, peer_port)

    def on_dccmsg(self, connection, event):
        try:
            if (len(event.arguments) != 1):
                print(f"Error while downloading, error: {event.arguments}")
                self.file.close()
                self.downloader.disconnect()
            data = event.arguments[0]
            self.file.write(data)
            self.received_bytes = self.received_bytes + len(data)

            if(self.received_bytes % 237 == 0):
                print(f"Downloaded Bytes: {self.received_bytes}")

            connection.send_bytes(struct.pack("!I", self.received_bytes))

            if (self.received_bytes == self.total_bytes):
                print("")
                print("Finished, disconnecting.")
                self.file.close()
                self.downloader.disconnect()
                if(self.queue.count() > 0):
                    c, e = self.queue[0]
                    self.on_ctcp(c, e)
                    self.queue.remove((c, e))
        except Exception as ex:
            print(f"Error: {ex}")

    def getFileSize(self, filename):
        if (os.path.exists(filename)):
            return os.stat(filename).st_size
        return 0

    def keepAlive(self, connection):
        connection.send_bytes(struct.pack("!I", self.received_bytes))
        self.dccTries =+ 1
        if(self.dccTries >= self.dccMaxTries):
            print("Reached max download attempts, closing connection.")
            self.file.close()
            self.downloader.disconnect()
        if(self.dccTimer is not None):
            self.dccTimer.stop()
        self.dccTimer = threading.Timer(3, self.keepAlive, [connection]).start()


    # def extractShowName(self, pubmsg):
    #     """
    #     :param pubmsg: The whole public message text that announced a new upload
    #     :return: The name of the show that was being uploaded.
    #     """

def determineStructure(filename):
    nameMatch = Niblette.showPattern.findall(filename)
    seasonMatch = Niblette.seasonPattern.findall(filename)
    season: str = "01"
    if (nameMatch):
        showName: str = nameMatch[0]
        showName = showName.strip()

        if (seasonMatch):
            season = seasonMatch[0].zfill(2)

        season = f"Season {season}"

        return showName, season
    return "", ""


def exit_handler():
    print("Exiting...")
    bot.file.close()
    bot.die()
    time.sleep(5)

def main():
    global bot
    global isWindows
    print("Niblette starting up.")

    if (os.name == 'nt'):
        print(f"Detected OS: Windows")
        isWindows = True
    else:
        print(f"Detected OS: Linux / Other")

    server = "irc.rizon.net"
    channel = "#NIBL"
    nickname = "Niblette"
    port = 6667

    atexit.register(exit_handler)

    print(f"Starting Bot.")
    bot = Niblette(channel, nickname, server, port)

    try:
        bot.start()
    except KeyboardInterrupt:
        bot.die()
    except Exception as ex:
        print(f"Error: {ex}")
        print("Attempting to restart bot.")
        bot.die()
        bot.start()


if __name__ == "__main__":
    main()