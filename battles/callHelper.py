from game import Game


def set_player_pos(x, y):
    Game.playerX = x
    Game.playerY = y


def set_player_pos_x(x):
    Game.playerX = x


def set_player_pos_y(y):
    Game.playerY = y


def get_player_x():
    return Game.playerX


def get_player_y():
    return Game.playerY


def get_keys():
    return Game.keys


def is_pressed(char):
    return Game.keys.contains(char)


def rotate(amt):
    Game.globalRotation = Game.globalRotation + amt


def set_rotation(amt):
    Game.globalRotation = amt


def get_rotation():
    return Game.globalRotation


def get_offset_x():
    return Game.globalOffsetX


def get_offset_y():
    return Game.globalOffsetY


def set_offset_x(amt):
    Game.globalOffsetX = amt


def set_offset_y(amt):
    Game.globalOffsetY = amt


def set_scale_x(amt):
    Game.globalScaleX = amt


def set_scale_y(amt):
    Game.globalScaleY = amt


def get_scale_x():
    return Game.globalScaleX


def get_scale_y():
    return Game.globalScaleY


def set_board_pos(x,y):
    Game.boardX = x
    Game.boardY = y


def set_board_x(val):
    Game.boardX = val


def set_board_y(val):
    Game.boardY = val


def get_board_x():
    return Game.boardX


def get_board_y():
    return Game.boardY


def set_board_size(x, y):
    Game.boardX = x
    Game.boardY = y


def set_board_size_x(amt):
    Game.boardX = amt


def set_board_size_y(amt):
    Game.boardY = amt
