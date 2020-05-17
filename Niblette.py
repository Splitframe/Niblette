import sys
import os
import shlex
import struct

import irc.bot
import irc.strings
from irc.client import ip_numstr_to_quad, ip_quad_to_numstr

class Niblette(irc.bot.SingleServerIRCBot):
    def __init__(self, channel, nickname, server, port=6667):
        irc.bot.SingleServerIRCBot.__init__(self, [(server, port)], nickname, nickname)
        self.channel = channel
        self.received_bytes = 0

    def on_nicknameinuse(self, connection, event):
        connection.nick(connection.get_nickname() + "_")

    def on_welcome(self, connection, event):
        connection.join(self.channel)

    def on_privmsg(self, connection, event):
        print(event.arguments[0])
        self.do_command(event, event.arguments[0])

    def on_pubmsg(self, connection, event):
        nickname = self.connection.get_nickname()
        a = event.arguments[0].split(":", 1)  # Message
        if (len(a) > 1 and irc.strings.lower(a[0]) == irc.strings.lower(nickname)):
            self.do_command(event, a[1].strip())
        return

    def do_command(self, event, cmd):
        nick = event.source.nick
        connection = self.connection

        if (cmd == "help"):
            connection.notice(nick, "This is a passive leech bot and has no active commands.")
            connection.notice(nick, "If you are the channel admin and have a problem just kick the bot.")
        elif (cmd == "test"):
            connection.privmsg("Ginpachi-Sensei", "/MSG Ginpachi-Sensei xdcc send 11018")
        else:
            connection.notice(nick, "Not understood: " + cmd)
            connection.notice(nick, "Send 'help' for more information")

    def on_ctcp(self, connection, event):
        if (len(event.arguments) < 2):
            print(event.arguments[0])
            return
        payload = event.arguments[1]
        parts = shlex.split(payload)
        command, filename, peer_address, peer_port, size = parts
        if (command != "SEND"):
            return
        self.filename = os.path.basename(filename)
        if (os.path.exists(self.filename)):
            print("A file named", self.filename, "already exists. Refusing to save it.")
            self.connection.quit()
            return
        self.file = open(self.filename, "wb")
        peer_address = irc.client.ip_numstr_to_quad(peer_address)
        peer_port = int(peer_port)
        self.dcc = self.dcc("raw").connect(peer_address, peer_port)

    def on_dccmsg(self, connection, event):
        data = event.arguments[0]
        self.file.write(data)
        self.received_bytes = self.received_bytes + len(data)
        self.dcc.send_bytes(struct.pack("!I", self.received_bytes))

def main():
    server = "irc.rizon.net"
    channel = "#NIBL"
    nickname = "Niblette"
    port = 6667

    bot = Niblette(channel, nickname, server, port)
    bot.start()

if __name__ == "__main__":
    main()
