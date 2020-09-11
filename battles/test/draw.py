from game import Game
from game import Display
from java.awt import Color
from java.awt import Point

Display.graphics2D.setColor(Color(255, 255, 255))

try:
    pointX = Game.memory.get('bullet1X')
    Display.graphics2D.fillRect(pointX, 0, 10, 10)
except:
    print("")

# for x in range(-5, 5):
#     for y in range(-10, 10):
#         hitX = (x + 1) * 20 + ((y % 2) * 10)
#         hitY = y * 10
#         if Game.playerX < hitX + 10:
#             if Game.playerX > hitX - 10:
#                 if Game.playerY > hitY - 10:
#                     if Game.playerY < hitY + 10:
#                         print("hello")
#         Display.graphics2D.fillRect(((x + 1) * 20 + ((y % 2) * 10)) - 5, (y * 10) - 5, 10, 10)
