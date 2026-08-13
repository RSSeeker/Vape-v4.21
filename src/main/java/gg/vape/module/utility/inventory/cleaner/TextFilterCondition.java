package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterCondition;
import gg.vape.module.utility.inventory.cleaner.TextMatchMode;
import gg.vape.utils.Base64Util;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public interface TextFilterCondition<T extends InventoryFilterCondition<T>>
extends InventoryFilterCondition<T> {
    public @UnmodifiableView List<String> getTexts();

    public T removeText(String text);

    public T addText(String text);

    public T withMatchMode(TextMatchMode mode);

    public TextMatchMode getMatchMode();

    public T clearText();

    @Override
    default public JsonObject toJson() {
        JsonObject jsonObject = InventoryFilterCondition.super.toJson();
        JsonArray jsonArray = new JsonArray();
        for (String text : this.getTexts()) {
            jsonArray.add((JsonElement)new JsonPrimitive(Base64Util.encodeUtf8Base64(text)));
        }
        jsonObject.addProperty("operator", this.getMatchMode().getName());
        jsonObject.add("text", (JsonElement)jsonArray);
        return jsonObject;
    }
}
