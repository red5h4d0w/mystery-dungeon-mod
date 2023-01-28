for d in */; do
    cd "$d"
    for i in *_p.png; do
        ffmpeg -i "$i" -vf "scale=iw/2:ih/2" -y "${i::-6}.png"
    done
    cd ..
done