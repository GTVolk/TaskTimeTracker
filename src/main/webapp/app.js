/*
 * This file launches the application by asking Ext JS to create
 * and launch() the Application class.
 */
Ext.application({
    extend: 'TTT.Application',

    name: 'TTT',

    requires: [
        // This will automatically load all classes in the TTT namespace
        // so that application classes do not need to require each other.
        'TTT.*'
    ]
});
