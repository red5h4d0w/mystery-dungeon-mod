cd mysteryDungeon/src/main/resources/mysteryDungeonResources/images/cards/
for d in */; do
    cd "$d"
    for i in *_p.png; do
        ffmpeg -i "$i" -vf "scale=iw/2:ih/2" -sws_flags bilinear -n "${i::-6}.png"
    done
    cd ..
done
cd ../../../../../../../