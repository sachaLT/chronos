# chronos
manage chronometers

This project is a exercice for using scala swing

# steps 1

create a window with one calendar conponent with this look :

JJ MMMMMMMM   HH:mm:SS

  YYYY

# steps 2

add a chronometer component with stop/start/pause/restart/reset options

# Swing constraints when running with sbt

Swing turn into is owner thread, so in order to remove the exception occuring when closing window we have to add this line in build.sbt :

fork in run := true

# Swing Frame methods are not totally connected

I had to connect the quit method to close operation because it's not do yet

override def closeOperation() { quit }
