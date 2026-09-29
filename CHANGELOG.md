# Changelog

All notable changes to Are You Sure? are documented here.

---

## [1.0.0] - 2026-09-30

### Added

- **Confirmation screen** - Attack, use and pick-block inputs are intercepted through the loader's
  `InteractionKeyMappingTriggered` event and cancelled until the player confirms.
- **Captcha step** - A random 6 character code drawn with colored, vertically scattered letters and
  noise dots. A wrong answer regenerates the code.
- **Single-use pass** - Solving the captcha allows exactly one action: the pass is consumed when the
  key is released, or expires after 10 seconds if unused.
- **Action description** - The prompt names the targeted block, entity or held item.
- **Translations** - English and French.
- **Loaders** - NeoForge and Forge for Minecraft 1.21.1 and 26.1.2. Client-side only.

### Ajouts

- **Écran de confirmation** - Les actions attaque, utilisation et pick-block sont interceptées via
  l'événement `InteractionKeyMappingTriggered` du loader et annulées jusqu'à confirmation.
- **Étape captcha** - Un code aléatoire de 6 caractères affiché avec des lettres colorées, décalées
  verticalement, et du bruit visuel. Une mauvaise réponse régénère le code.
- **Passe à usage unique** - Résoudre le captcha autorise une seule action : le passe est consommé au
  relâchement de la touche, ou expire après 10 secondes s'il n'est pas utilisé.
- **Description de l'action** - Le message nomme le bloc, l'entité ou l'objet ciblé.
- **Traductions** - Anglais et français.
- **Loaders** - NeoForge et Forge pour Minecraft 1.21.1 et 26.1.2. Uniquement côté client.

---
