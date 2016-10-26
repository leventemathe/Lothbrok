# Lothbrok
Lothbrok is (going to be) a 2D platform brawler game for PC (Windows, Linux, macOS), Android and iOS.
It's my BSc thesis, and is very much a work in progress.
I'm using libGDX, a cross-platform Java framework for 2D game development.
(https://libgdx.badlogicgames.com/)
![menu](https://github.com/leviouss/Lothbrok/blob/master/doc/img/menu.png "Main menu")
![game](https://github.com/leviouss/Lothbrok/blob/master/doc/img/wip_game.png "Game WIP")
## Current
Currently I'm working on loading animations, maps, and basic game logic. I would like to make the textures and the game scalable to all the different resolutions ASAP.
## Animation
I'm using Spriter to create bone animations. (https://brashmonkey.com/)
To load and play them, I utilize this generic Java implementation: https://github.com/Trixt0r/spriter.
I also added my own wrapper class, to better encapsulate (for me anyway) the functionalities.
## Maps
I edit maps with Tiled, and load them with the libGDX implementation. (http://www.mapeditor.org/)
I will have to tinker with the xml files a bit, as they don't support scaling and multiple texutre atlas sets.
## Architecture
I'm implementing my own MVC-ish architecture. The model uses simple state machines to represent what it's doing. I'm experimenting with a command pattern implementation as part of the Controller layer, and the View is completeley seperated from the model. 
The Screen classes tie these together. 
For example, in the game screen we have the player model, the player controller, and the player assets. The controller (in this case the keyboard, touchscreen etc.) sends commands to the player, and the renderer then draws the appropriate assets for the player's state.
