# Contributing

Thanks for wanting to make Minecraft even more annoying.

## Repository layout

- `main` holds every loader/version in its own folder, plus the shared documentation.
- One branch per target (`neoforge-1.21.1`, `forge-1.21.1`, `neoforge-26.1.2`, `forge-26.1.2`)
  holds that single project at the repository root.

A change to gameplay logic must be ported to all four folders. The code is intentionally kept
almost identical between them; only the loader glue (`AreYouSure.java`, event types) and the
26.1 GUI API (`GuiGraphicsExtractor`, `KeyEvent`) differ.

## Workflow

1. Fork the repository and create a branch from `main`: `feat/short-name` or `fix/short-name`.
2. Make your change in every affected folder.
3. Build each project you touched: `./gradlew build`.
4. Test in game (`./gradlew runClient`) at least on one 1.21.1 and one 26.1.2 target.
5. Update `CHANGELOG.md` (English and French sections).
6. Open a pull request using the template.

## Commit messages

`type: descriptive message` in English. Types: `feat`, `fix`, `docs`, `chore`, `refactor`, `ci`.

## Translations

Add a file in `src/main/resources/assets/areyousure/lang/` of every folder, using the keys from
`en_us.json`.

---

# Contribuer

Merci de vouloir rendre Minecraft encore plus pénible.

## Organisation du dépôt

- `main` contient chaque loader/version dans son propre dossier, plus la documentation commune.
- Une branche par cible (`neoforge-1.21.1`, `forge-1.21.1`, `neoforge-26.1.2`, `forge-26.1.2`)
  contient ce seul projet à la racine.

Toute modification de logique doit être portée dans les quatre dossiers. Seuls le code d'amorçage
du loader et l'API GUI de 26.1 diffèrent.

## Déroulement

1. Forkez le dépôt et créez une branche depuis `main` : `feat/nom-court` ou `fix/nom-court`.
2. Appliquez la modification dans chaque dossier concerné.
3. Compilez chaque projet touché : `./gradlew build`.
4. Testez en jeu (`./gradlew runClient`) sur au moins une cible 1.21.1 et une 26.1.2.
5. Mettez à jour `CHANGELOG.md` (sections anglaise et française).
6. Ouvrez une pull request avec le modèle fourni.

## Messages de commit

`type: message descriptif` en anglais.
