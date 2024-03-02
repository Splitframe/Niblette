package de.niblette.modules.irc

data class IrcConfig(
    val server: String,
    val port: String,
    val channels: List<String>,
    val regex: List<Regex>,
)

/*
    botPattern    = re.compile(r"(?<=MSG CR-HOLLAND\|NEW ).*$")
    showPattern   = re.compile(r"(?<=\])([^-]{3,}?)(?=\-)")
    seasonPattern = re.compile(r"S(\d+)")
 */