
if [ "$#" -eq  0 ]; then
    echo "USAGE: sh run_zelda.sh OPTION_NUMBER \n where OPTION_NUMBER could be:\n 0 (just server)\n 1 (just client)\n 2 (both)\n 3 (server and two clients)\n"
    exit 1
fi

OPT=$1
BINARY_PATH=${PWD}
JPATH="/System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/bin"
ZSERVER_PATH="$BINARY_PATH/silly/server/ZeldaUDPServer.class"
if [ ! -f "$ZSERVER_PATH" ]; then
    echo "find zelda server binary failed"
    echo "this script wants to be in the bin directory. \n looks for a folder named 'silly' and a file named 'ZeldaUDPServer.class'"
    echo "(Or just edit the variable BINARY_PATH as needed)"
    exit 1
fi
if [ ! -d "$JPATH" ]; then
    echo "The path to the java virtual machine doesn't seem to exist. \nThe directory that we tried:"
    echo "$JPATH"
    echo "These programs are untested on all JVMs except 1.6"
    exit 1
fi
JPATH="$JPATH/java"
echo "$OPT"
if [ "$OPT" -ne 1 ]; then
    $JPATH -Dfile.encoding=MacRoman -classpath $BINARY_PATH  silly.server.ZeldaUDPServer &
    read -t1 -n1 -r -p "waiting one seconds..." key
fi
if [ "$OPT" -gt 0 ]; then
    $JPATH -Dfile.encoding=MacRoman -classpath $BINARY_PATH  silly.SillyFrame &
    read -t1 -n1 -r -p "waiting one seconds..." key
fi
if [ "$OPT" -eq 3 ]; then
    $JPATH -Dfile.encoding=MacRoman -classpath $BINARY_PATH  silly.SillyFrame &
fi

echo "done"
echo "NOTE: the path to the binary can't have spaces in it. \nThis is the path that was used: \n $BINARY_PATH" 