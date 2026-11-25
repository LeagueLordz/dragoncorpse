# Dragon Corpse (Minecraft 1.20.4, Fabric)

Command: `/dragoncorpse [neck] [pitch] [roll] [yoffset]`  (op level 2)

- neck    : neck/head ko neeche jhukana (default 1.0, positive = neeche)
- pitch   : poori body nose-down angle, degrees (default 0)
- roll    : body roll, degrees (180 = ulta/belly up) (default 0)
- yoffset : model ko upar/neeche karna, blocks (default 1.0)

Tune karne ke liye: `/kill @e[type=dragoncorpse:dragon_corpse]` karke naye numbers ke saath dubara spawn karo.
Corpse invulnerable hai, hilta nahi, aur world save/load mein bachi rehti hai.

Build: GitHub pe push karo -> Actions tab -> `dragoncorpse-jar` artifact download karo.
(Local: `gradle build`, jar `build/libs/` mein.)
