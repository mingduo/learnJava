#!/bin/bash
# @Function
# show all console text color themes.
#
# @online-doc https://github.com/oldratlee/useful-scripts/blob/master/docs/shell.md#-console-text-color-themessh
# @author Jerry Lee (oldratlee at gmail dot com)

readonly _ctct_PROG="$(basename "$(readlink -f "$0")")"
[ "$_ctct_PROG" == 'console-text-color-themes.sh'  ] && readonly _ctct_is_direct_run=true

readonly _ctct_ec=$'\033' # escape char
readonly _ctct_eend=$'\033[0m' # escape end

colorEcho() {
    local combination="$1"
    shift 1

    [ -t 1 ] && echo "$_ctct_ec[${combination}m$@$_ctct_eend" || echo "$@"
}

colorEchoWithoutNewLine() {
    local combination="$1"
    shift 1

    [ -t 1 ] && echo -n "$_ctct_ec[${combination}m$@$_ctct_eend" || echo -n "$@"
}

# if not directly run this script(use as lib), just export 2 helper functions,
# and do NOT print anything.
[ "$_ctct_is_direct_run" == "true" ] && {
    for style in 0 1 2 3 4 5 6 7; do
        for fg in 30 31 32 33 34 35 36 37; do
            for bg in 40 41 42 43 44 45 46 47; do
                combination="${style};${fg};${bg}"
                colorEchoWithoutNewLine "$combination" "$combination"
                echo -n " "
            done
            echo
        done
        echo
    done

    echo "Code sample to print color text:"

    echo -n '    echo -e "\033['
    colorEchoWithoutNewLine "3;35;40" "1;36;41"
    echo -n "m"
    colorEchoWithoutNewLine "0;32;40" "Sample Text"
    echo "\033[0m\""

    echo -n "    echo \$'\033["
    colorEchoWithoutNewLine "3;35;40" "1;36;41"
    echo -n "m'\""
    colorEchoWithoutNewLine "0;32;40" "Sample Text"
    echo "\"$'\033[0m'"
    echo "      # NOTE: $'foo' is the escape sequence syntax of bash, safer escape"

    echo "Output of above code:"
    echo "    $_ctct_ec[1;36;41mSample Text$_ctct_eend"
    echo
    echo "If you are going crazy to write text in escapes string like me,"
    echo "you can use colorEcho and colorEchoWithoutNewLine function in this script."
    echo
    echo "Code sample to print color text:"
    echo '    colorEcho "1;36;41" "Sample Text"'
    echo "Output of above code:"
    echo -n "    "
    colorEcho "1;36;41" "Sample Text"
}
