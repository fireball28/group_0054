package conferencesim.usecases;

import conferencesim.entities.eventpack.Room;

public interface RoomUpdater {
    void updateRoom(Room rm);
    void removeRoomObs (Room rm);
}
