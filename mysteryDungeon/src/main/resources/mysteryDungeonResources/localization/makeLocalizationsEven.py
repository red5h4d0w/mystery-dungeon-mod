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
                    if engJsonPath in localizationFileJsonsPath:
                        print(engJsonPath)
                        #TODO create actual code that evens the files
                    else:
                        print("creating file")
                        shutil.copyfile(os.path.join(os.path.dirname(os.path.realpath(__file__)), "eng", engJsonPath), os.path.join(os.path.dirname(os.path.realpath(__file__)), entry.name, engJsonPath))
            