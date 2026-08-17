#include "controller_model.h"
#include "controller_ui.h"

#include <windows.h>

int APIENTRY wWinMain(HINSTANCE instance, HINSTANCE, wchar_t*, int showCommand) {
    ControllerModel model;
    ControllerUi ui(instance, model);
    return ui.run(showCommand);
}
