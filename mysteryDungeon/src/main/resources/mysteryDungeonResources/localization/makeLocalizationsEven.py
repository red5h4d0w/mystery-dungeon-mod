import json
import os
import shutil

# TODO: look for change in Desc types for cards
# TODO: look for deprrecated cards and remove them

with os.scandir(os.path.join(os.path.dirname(os.path.realpath(__file__)), "eng")) as engDir:
    engJsonsPath = []
    for entry in engDir:
        engJsonsPath.append(entry.name)
with os.scandir(os.path.dirname(os.path.realpath(__file__))) as it:
    for entry in it:
        if entry.is_dir() and entry.name != "eng":
            with os.scandir(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name)) as toLocalizeDir:
                localizationFileJsonsPath = [f.name for f in toLocalizeDir]
                for engJsonPath in engJsonsPath:
                    # Update file if it exists
                    if engJsonPath in localizationFileJsonsPath:
                        # Update a keyword file
                        if engJsonPath == "MysteryDungeon-Keyword-Strings.json":
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "eng", "MysteryDungeon-Keyword-Strings.json"), "r") as f:
                                engJson = json.loads(f.read())
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, "MysteryDungeon-Keyword-Strings.json"), "r") as f:
                                localizedJson = json.loads(f.read())
                                originalLocalizedJson = localizedJson
                            for keyword in engJson:
                                if not keyword["PROPER_NAME"] in map(lambda x: x["PROPER_NAME"], localizedJson):
                                    localizedJson.append(keyword)
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, "MysteryDungeon-Keyword-Strings.json"), "w") as jsonFile:
                                json.dump(localizedJson, jsonFile,
                                          indent=4, ensure_ascii=False)
                        # update Questions.json
                        elif engJsonPath == "Questions.json":
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "eng", "Questions.json"), "r") as f:
                                engJson = json.loads(f.read())
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, "Questions.json"), "r") as f:
                                localizedJson = json.loads(f.read())
                                originalLocalizedJson = localizedJson
                            for (index, question) in enumerate(engJson):
                                # Ensure POINTS are not changed
                                if "POINTS" in question:
                                    localizedJson[index]["POINTS"] = question["POINTS"]
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, "Questions.json"), "w") as jsonFile:
                                json.dump(localizedJson, jsonFile,
                                          indent=4, ensure_ascii=False)
                        # Touch every other file
                        else:
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "eng", engJsonPath), "r") as f:
                                engJson = json.loads(f.read())
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, engJsonPath), "r") as f:
                                localizedJson = json.loads(f.read())
                                originalLocalizedJson = localizedJson
                            for (key, value) in engJson.items():
                                if not key in localizedJson.keys():
                                    localizedJson[key] = value
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, engJsonPath), "w") as jsonFile:
                                json.dump(localizedJson, jsonFile,
                                          indent=4, ensure_ascii=False)

                    # Copy file if file does not exist
                    else:
                        print("creating file")
                        shutil.copyfile(os.path.join(os.path.dirname(os.path.realpath(__file__)), "eng", engJsonPath), os.path.join(
                            os.path.dirname(os.path.realpath(__file__)), entry.name, engJsonPath))
