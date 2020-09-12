# from battles.callHelper import *
from game import Game
from java.lang import Math


# this is called every Game.frame
Game.soulType = 1

if not Game.memory.containsKey('bullet1'):
    Game.memory.put('bullet1X', 0)
    Game.memory.put('bullet1Y', 0)

Game.boardX = Math.cos(Math.toRadians(Game.frameNumber / 25.6)) * 32.0
Game.boardY = (Math.sin(Math.toRadians(Game.frameNumber / 20.0)) * 32.0) + 100.0
Game.boardWidth = int(((649.0 / 2.0) + Math.sin(Game.frameNumber / 256.0) * 64.0))
Game.boardHeight = int(((385.0 / 2.0) + Math.cos(Game.frameNumber / 256.0) * 32.0))
Game.globalRotation = Math.cos(Game.frameNumber / 350.0) * 36.0
Game.globalScaleX = (Math.sin(Game.frameNumber / 3022.0) * 0.5) + 1
Game.globalScaleY = (Math.cos(Game.frameNumber / 322.0) * 0.2) + 1
