import string
import sys
import os
import shlex
import struct
import atexit
import re

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

    pattern = re.compile(r"(?<=MSG CR-HOLLAND\|NEW ).*$")

    def __init__(self, channel, nickname, server, port=6667):
        irc.bot.SingleServerIRCBot.__init__(self, [(server, port)], nickname, nickname)
        self.connection.buffer_class = buffer.LenientDecodingLineBuffer
        self.channel = channel
        self.received_bytes = 0

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
        match = Niblette.pattern.findall(message.strip("'"))
        if(match):
            print(f"Regex Match, output: {match[0]}")
            connection.privmsg(nickname, f"Regex match output: {match[0]}")

    def on_pubmsg(self, connection, event):

        nickname = event.source.nick
        message:str = event.arguments[0]  # Message
        print(f"Sender: {nickname}, Message: {message}")
        if (nickname == "CR-HOLLAND|NEW"):
            if ("(1080p)" in message):
                print(f"Relevant Message from Source Bot: {message}.")
                match = Niblette.pattern.findall(message.strip("'"))
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
        if (isWindows):
            fullpath = os.path.basename(filename)
        else:
            fullpath = os.path.basename("/usr/download/" + filename)
        print("Receiving a file. Potential location: ", fullpath)
        if (os.path.exists(fullpath)):
            print("A file named", fullpath, "already exists. Refusing to save it.")
            return
        #TODO:
        # Implement multi download functionality.
        # Currently the system is vulnerable to multiple simultaneous connections.
        # The received_bytes get mangled.
        self.received_bytes = 0
        self.file = open(fullpath, "wb")
        peer_address = irc.client.ip_numstr_to_quad(peer_address)
        peer_port = int(peer_port)
        self.dcc = self.dcc("raw").connect(peer_address, peer_port)

    def on_dccmsg(self, connection, event):
        data = event.arguments[0]
        self.file.write(data)
        self.received_bytes = self.received_bytes + len(data)
        self.dcc.send_bytes(struct.pack("!I", self.received_bytes))

    def getFileSize(self, filename):
        if (os.path.exists(filename)):
            return os.stat(filename).st_size
        return 0

    # def extractShowName(self, pubmsg):
    #     """
    #     :param pubmsg: The whole public message text that announced a new upload
    #     :return: The name of the show that was being uploaded.
    #     """

def exit_handler():
    print("Exiting...")
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