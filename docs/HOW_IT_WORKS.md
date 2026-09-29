# How it works

## Overview

The mod is client-side only and made of three classes:

| Class          | Role                                                                 |
|----------------|----------------------------------------------------------------------|
| `AreYouSure`   | Mod entry point. Registers the listeners on the client only.         |
| `ActionGuard`  | Intercepts inputs, opens the screen, manages the single-use pass.    |
| `SureScreen`   | The two-step screen: confirmation, then captcha.                     |

## Input interception

Both loaders fire `InputEvent.InteractionKeyMappingTriggered` whenever Minecraft is about to run
an attack (left click, including every tick of block breaking), a use (right click, once per hand)
or a pick-block (middle click). `ActionGuard` cancels the event and disables the hand swing, then
opens `SureScreen` if no screen is open yet.

- NeoForge: `event.setCanceled(true)` on `NeoForge.EVENT_BUS`.
- Forge 1.21.1: `event.setCanceled(true)` on `MinecraftForge.EVENT_BUS`.
- Forge 26.1.2 (EventBus 7): the listener is a predicate registered on
  `InteractionKeyMappingTriggered.BUS`; returning `true` cancels.

## The pass

When the captcha is solved, `ActionGuard.grant(key)` stores the key mapping that triggered the
screen and a 10 second deadline. The next event for that key is allowed and marks the pass as in
use. While the key is held, events keep passing (so a block can be fully mined). On the first
client tick where the key is no longer down, the pass is consumed.

## The captcha

- Alphabet without ambiguous characters (`0/O`, `1/I`) : `ABCDEFGHJKLMNPQRSTUVWXYZ23456789`.
- 6 characters, each with a random color and a vertical offset between -4 and +4 pixels.
- 40 translucent noise dots and a strike-through line.
- The answer is case-insensitive. A wrong answer regenerates everything.

## Version differences

Minecraft 26.1 renamed the GUI rendering API: `GuiGraphics` became `GuiGraphicsExtractor`,
`Screen.render` became `extractRenderState`, `drawString` / `drawCenteredString` became `text` /
`centeredText`, and `keyPressed` now takes a `KeyEvent` record.

---

# Fonctionnement

Le mod est uniquement côté client. `ActionGuard` écoute l'événement
`InteractionKeyMappingTriggered` (attaque, utilisation, pick-block), l'annule et ouvre `SureScreen`.
Une fois le captcha réussi, un passe est accordé pour la touche concernée : il reste valable tant
que la touche est maintenue (pour pouvoir miner un bloc entier) et il est consommé au relâchement,
ou au bout de 10 secondes s'il n'est pas utilisé.

Le captcha utilise un alphabet sans caractères ambigus, 6 caractères colorés et décalés, du bruit
visuel et une ligne barrée. Une mauvaise réponse régénère un code.

Minecraft 26.1 a renommé l'API de rendu GUI (`GuiGraphicsExtractor`, `extractRenderState`,
`text`, `centeredText`, `KeyEvent`), d'où les petites différences entre les dossiers.
