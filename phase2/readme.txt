                        *************************************
                        How to use Conference Simulator 2000:
                        *************************************


            Link to video tutorial: https://drive.google.com/file/d/1LCaF40868s1XEmpAr6U-dlSUCe1tF7e0/view?usp=sharing
            Link to UML Diagram: https://www.dropbox.com/s/2acptg4lhydeze4/design.pdf?dl=0



An admin account is provided by default the credentials are:

    admin
    8888

The admin account acts as an organizer and has all the same powers.

At startup you have three options:

    -Login
    -Register
    -quit (q)

The register command will only create attendees.
To create an organizer (or speaker for that matter), please log in as the default admin.

Once logged in, please type "help" to see the command list.

******************************
Commands (in general)
******************************

The command list will be displayed and commands are group by category.
When a command requires arguments, it will be displayed as such:

commandName <arg1> <arg2>

If an argument is optional, it will be indicated by a *

command example for Organizer/Admin:

    createaccount <userID> <password> <role>



******************************
Creating accounts
******************************

To create an attendee: Select register at login or log in as the admin and use the createaccount command
To create an organizer: Log in as the admin and use the createaccount command, remember to specify the role.
To create a speaker: Log in as either the admin or an organizer and use the createaccount command

Organizers have now access to user statistics like, how many users and speakers have registered.


******************************
Adding friends
******************************

If more users have registered, using addfriend and specifying their username will add them as a friend to your list
To remove them from your friends' list, use removefriend followed by their username.

Using numFriends will display how many friends the current user has.


******************************
Messaging
******************************

Organizers and Speakers have different messaging options from Attendees, please type help to see all available commands
Messages now can be marked as unread, archived, unarchived.


******************************
Adding Rooms
******************************

To add a room, log in as an organizer or admin and use the addroom command
and specify the room name, followed by the capacity and the kind of the room.

There are three kinds of rooms to choose from:

    -Classroom
    -Auditorium
    -Court

Each kind of room has it's own technologies that are available to the space.
Once a room is created, using viewRoomTechnologies will display what is available to a given room.


******************************
Adding Events
******************************

To add an event, please type the addevent command and specify the following:

    <eventName> <eventLocation> <startTime "yyyy-MM-dd HH:mm"> <endTime "yyyy-MM-dd HH:mm"> <speakerIDs* id1,id2,...,id3>

    -Note that speakers are optional, as not every event requires one.
    -Note the start and end times must be typed in yyyy-MM-dd HH:mm format and wrapped in double quotes

Organizers have now access to event statistics like, most attended event (single or top5 list), event with most speakers, etc.


******************************
Attending Events
******************************

Once an event is added, an attendee can sign up for it by using the attendevent command.
If an attendee wants to cancel their spot, they must use the cancelattend command.
Using getFellowAttendees will reveal other users who signed up for that event.


******************************
Requests
******************************

An Organizer can create a request, either a DietaryRequest or AccessibilityRequest, that is 
requested by the user. Requests can have the status "pending" or "addressed", which Organizers can also change.
See "help" for more options while logged in as an organizer


******************************
Quitting and Saving
******************************

The program will only save when it quits.
Logout and select "q"
Once the program is run again, the saved info will be loaded and all rooms, messages, users and events will be
back.