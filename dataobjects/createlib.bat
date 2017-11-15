:Autor OLEG KARLOVSKIY
@echo off
cd build\classes\main\
jar cvmf ..\..\..\MANIFEST.MF ..\..\..\..\Externals\dataobjects.jar com ..\..\..\libs
echo COMPLETE!
set /p DUMMY=Hit ENTER to continue.