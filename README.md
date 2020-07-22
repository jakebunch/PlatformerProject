Summer 2020 Platformer Game Project

  I'm going to be periodically updating these notes with my current goals/vision for this game, which could easily change over time,
as I progress with development. I'll also give a bit of an introduction as to why I decided to do this in case anybody is actually
interested in reading this! I spent a lot of the last month burying my face in online tutorials about the conventions and design patterns
involving game development, so even though I'm starting these notes late, I have already had to do some major refactoring after learning
so much. I've also realized that keeping notes about my progress might be a good way to keep myself motivated and provide a nice way to look
back on everything after I finish (and I sincerely hope that I do).

  Ever since I can remember, I have been enthralled by video games. It wasn't until middle school however, that I discovered the program
GameMaker, a super user-friendly game development environment that is, surprisingly, still popular among professional indie developers
today. Even though I was only able to teach myself the extremely limited drag-and-drop development mode, I was instantly hooked. I made
tons of half-finished games that were never really played by anyone besides my friends, but I loved it. Seeing every terrible animation
I made dance across the screen and figuring out what felt like puzzles on how to make my character move around was incredible satisfying.
In fact, I still believe that it's the exact reason I'm studying software design today. Now, after finishing my third year as a Computer
Engineering student at UT Austin, I feel confident that I have the knowledge necessary to create what I hope to be a real, polished video
game that I can call my own. Since quarantine has prevented me from doing much else, this also seems like the perfect time to work on a
project like this. I also should definitely mention and thank the creators of the game "Celeste", Matt Thorson and Noel Berry, for creating
an absolutely incredible game that inspired me to make this one. They also deserve a ton of credit for being so open about their design
process, providing some much needed help to beginner developers like myself (not that they or anyone else is actually going to read this lol).
If you've gotten this far, thanks so much for reading, and I hope you enjoy the game or whatever I eventually decide to call it!

#############################################################################################################################################

7/22/20--------------------------------------------------------------------------------------------------------------------------------------

WHAT I HAVE

- Solid codebase for my game including things like:
  - main game loop
  - rendering things efficiently
  - loading worlds/tile sets
  - entity class structures
  - managing entities
  - a working 2-D physics system with Tiles, Solids, and Actors
- Working platforming demo with ability to edit and load worlds from a txt document
- Temporary animated sprites

CURRENT GOALS

- get full levels working
  - draw a background for level 1 (maybe figure out how to accomplish a parallax effect?)
  - figure out how to create a level with multiple "rooms"
  - Only be render/focus on objects in the current "room"
  - Smooth room transitions, like in Celeste
- work on player class
  - implement crouching and staff-dashing
  - implement wall sliding and wall jumping
  - implement temporary dying animation
  - fix random double jump issue: might have something to do with coyote time and vaulting
