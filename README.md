<div align="center">

# VoxChat

### A configurable chat plugin for Minecraft servers

Local and global chat, private messages, custom join and quit notices, and more.

</div>

---

## Key Features

- **Local chat** — messages reach players in the same world within a configurable radius.
- **Global chat** — begin a message with `!` to send it to every online player.
- **Private messages** — message online players with configurable templates and an optional sound.
- **Lifecycle messages** — customize player join, quit, and death messages.
- **Simple administration** — reload the plugin configuration with `/voxchat reload`.
- **bStats metrics** — collect standard plugin usage statistics.

---

## Functionality

Messages without a prefix use local chat. By default, players within 50 blocks in the same world receive the message. The sender sees it too. If nobody is nearby, the sender receives a configurable notice.

Start a message with `!` to use global chat. The exclamation mark is removed before sending. The configured sign is colored by dimension: green in the Overworld, red in the Nether, and purple in the End.

Join, quit, and death messages can each be enabled or disabled independently. The death message can include the server's original death text.

---

## Configuration

The default configuration is created at `plugins/vox-chat/config.yml`.

| Setting | Default | Description |
| --- | --- | --- |
| `local-chat.enable` | `true` | Enable local chat. |
| `local-chat.radius` | `50` | Local chat radius in blocks. |
| `local-chat.message` | `§7{player} >> {message}` | Local chat format. |
| `global-chat.enable` | `true` | Enable global chat with the `!` prefix. |
| `global-chat.sign` | `●` | Sign used in the global chat prefix. |
| `global-chat.message` | `{world_prefix} §f{player} >> {message}` | Global chat format. |
| `join.enable` | `true` | Show a custom join message. |
| `join.message` | `§a[+] §f{player}` | Join message format. |
| `quit.enable` | `true` | Show a custom quit message. |
| `quit.message` | `§c[-] §f{player}` | Quit message format. |
| `death.enable` | `true` | Show a custom death message. |
| `death.sign` | `[☠]` | Sign used in the death message. |
| `death.message` | `{sign} {player} {default_message}` | Death message format. |
| `private-message.enable` | `true` | Enable private messages. |
| `private-message.sign` | `[✉]` | Sign used in private messages. |
| `private-message.template-receiver` | `§b{sign} {sender} >> {message}` | Message shown to the receiver. |
| `private-message.template-sender` | `§b{sign} {sender} -> {receiver} >> {message}` | Confirmation shown to the sender. |
| `private-message.sound` | `true` | Play a sound for the receiver. |
| `message.noReceivers` | — | Notice shown when nobody is in local chat range. |
| `message.pluginReloaded` | — | Confirmation after a configuration reload. |
| `message.permissionError` | — | Notice shown when reload permission is missing. |
| `message.emptyMessage` | — | Notice shown when a private message is empty. |
| `message.receiverIsOffline` | — | Notice shown when the recipient is offline. |

Message templates support these placeholders where applicable:

| Placeholder | Meaning |
| --- | --- |
| `{player}` | Player display name. |
| `{message}` | Chat or private message text. |
| `{world_prefix}` | Colored global chat sign for the sender's world. |
| `{sign}` | Configured sign for deaths or private messages. |
| `{default_message}` | Minecraft's original death message. |
| `{sender}` | Private message sender's display name. |
| `{receiver}` | Private message receiver's display name. |

Use Minecraft color codes such as `§a` in message strings. After editing the file, run `/voxchat reload` to apply the changes.

---

## Commands

| Command | Aliases | Description |
| --- | --- | --- |
| `/message <player> <message>` | `/msg`, `/tell`, `/w`, `/tellraw` | Send a private message. |
| `/voxchat reload` | `/vox reload`, `/vc reload` | Reload the plugin configuration. |

The `voxchat.reload` permission is required to reload the configuration. It is granted to operators by default.

---
