package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.module.utility.inventory.cleaner.TextFilterCondition;
import gg.vape.module.utility.inventory.cleaner.TextMatchMode;
import gg.vape.utils.Base64Util;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTextFilterCondition<T extends AbstractTextFilterCondition<T>>
implements TextFilterCondition<T> {
    private List<String> texts = new ArrayList<String>();
    private TextMatchMode matchMode = TextMatchMode.EQUALS;

    @Override
    public TextMatchMode getMatchMode() {
        return this.matchMode;
    }

    @Override
    public List<String> getTexts() {
        return this.texts;
    }

    public T withMatchMode(TextMatchMode matchMode) {
        this.matchMode = matchMode;
        return (T)this;
    }

    public T removeText(String text) {
        this.texts.remove(text);
        return (T)this;
    }

    protected AbstractTextFilterCondition(List<String> texts, TextMatchMode matchMode) {
        this.texts = new ArrayList<String>(texts);
        this.matchMode = matchMode;
    }

    public T clearText() {
        this.texts.clear();
        return (T)this;
    }

    protected AbstractTextFilterCondition(JsonObject jsonObject) {
        JsonArray jsonArray = ConfigJsonUtils.getJsonArray(jsonObject, "text");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); ++i) {
                this.texts.add(Base64Util.decodeUtf8Base64(jsonArray.get(i).getAsString()));
            }
        }
        this.matchMode = TextMatchMode.fromName(jsonObject.get("operator").getAsString());
    }


    public T addText(String text) {
        this.texts.add(text);
        return (T)this;
    }

    protected AbstractTextFilterCondition() {
    }
}
