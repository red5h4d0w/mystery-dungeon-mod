import json

with open("mysteryDungeon/src/main/resources/ModTheSpire.json", "r") as f:
    jsonString = json.loads(f.read())
    version_number = jsonString["version"].split(".")
    version_number[2] = str(int(version_number[2])+1)
    jsonString["version"] = ".".join(version_number)

with open("mysteryDungeon/src/main/resources/ModTheSpire.json", "w") as f:
    json.dump(jsonString, f, indent=4, ensure_ascii=False)