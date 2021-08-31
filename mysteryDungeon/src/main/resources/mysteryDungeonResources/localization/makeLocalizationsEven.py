import json
import os
import shutil

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
                            print(engJson[0])
                            for keyword in engJson:
                                if not keyword["PROPER_NAME"] in map(lambda x:x["PROPER_NAME"], localizedJson):
                                    localizedJson.append(keyword)
                            with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, "MysteryDungeon-Keyword-Strings.json"), "w") as jsonFile:
                                json.dump(localizedJson, jsonFile, indent=4)
                        #TODO create actual code that evens the files

                    # Copy file if not
                    else:
                        print("creating file")
                        shutil.copyfile(os.path.join(os.path.dirname(os.path.realpath(__file__)), "eng", engJsonPath), os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, engJsonPath))
            