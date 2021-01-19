Instructions for use:

Upon startup, the user is presented with three options:

    * Login
    * Register
    * Quit

The user must type the command as spelled in the prompt.

If this is the user's first time using the program, they must first register for an account following
the prompts printed on the screen.

When an account is created, at this stage, the users can log in as Organizers or Attendees at will.

After logged in, please type 'help' to see a list of possible commands.

Attendees and Organizers have different commands.

The speaker account can only be created by Organizers

..
.

Adding an event:

- Organizers must first create the Speaker's account (createSpeakerAccount)
- Organizers must first add the room where the (addroom)
- Organizers then must add the event (addevent)

* If the room is occupied at the given time, the event won't be added.
* If the Speaker is already committed at a different room in the same start time, the event won't be added.

Events are assumed to be one hour long.


..
.

Attending an event:

Once an event is added attendees can sign up for it by using (attendEvent) and typing the eventID. An attendee can
see all events and their IDs by selecting (viewAllEvents).

Using (getFellowAttendees) will allow the user to see all other users attending the same events they signed
up for.

An attendee can cancel their spot by selecting (cancelAttend) and inserting the EventID for the event at hand.


..
.

Messaging users:

Select the adequate prompt for the desired action - type (help) for command list and type the UserID of recipient
After wards type the message and hit return or enter