package gg.vape.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.module.Macro;
import gg.vape.ui.click.frame.impl.FrameMacros;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MacroManager {
    private final Set<Macro> macros = new LinkedHashSet<Macro>();

    public void removeMacro(Macro macro) {
        this.macros.remove(macro);
        FrameMacros.jo.Z(macro);
    }

    public void addMacro(Macro macro) {
        this.macros.add(macro);
        FrameMacros.jo.v(macro);
    }

    public Macro getMacro(String name) {
        for (Macro macro : this.macros) {
            if (!macro.getName().equalsIgnoreCase(name)) continue;
            return macro;
        }
        return null;
    }


    private void clear() {
        for (Macro macro : new HashSet<Macro>(this.macros)) {
            this.removeMacro(macro);
        }
    }

    public List<Macro> getMacros(List<Integer> boundInputs) {
        ArrayList<Macro> matches = new ArrayList<Macro>();
        for (Macro macro : this.macros) {
            if (!macro.getBoundInputs().equals(boundInputs)) continue;
            matches.add(macro);
        }
        return matches;
    }

    public List<Macro> getMacros(int inputCode) {
        ArrayList<Macro> matches = new ArrayList<Macro>();
        for (Macro macro : this.macros) {
            if (!macro.getBoundInputs().contains(inputCode)) continue;
            matches.add(macro);
        }
        return matches;
    }

    public JsonArray toJson() {
        JsonArray result = new JsonArray();
        for (Macro macro : this.macros) {
            result.add((JsonElement)macro.toJson());
        }
        return result;
    }

    public Set<Macro> getMacros() {
        return this.macros;
    }

    public void loadJson(JsonArray serializedMacros) {
        this.clear();
        for (int index = 0; index < serializedMacros.size(); ++index) {
            JsonObject serializedMacro;
            JsonElement element = serializedMacros.get(index);
            if (!element.isJsonObject() || element.isJsonNull() || (serializedMacro = element.getAsJsonObject()).get("name") == null || serializedMacro.get("name").isJsonNull()) continue;
            String name = ConfigJsonUtils.getDecodedStringOrEmpty(serializedMacro, "name");
            Macro macro = Macro.create(name);
            macro.loadJson(serializedMacro);
            this.addMacro(macro);
        }
    }
}

