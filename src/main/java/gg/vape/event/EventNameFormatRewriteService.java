package gg.vape.event;

import gg.vape.Vape;
import gg.vape.event.impl.EventChat;
import gg.vape.event.impl.EventChatMessageRender;
import gg.vape.event.impl.EventEntityRenderState;
import gg.vape.event.impl.EventNameFormat;
import gg.vape.event.impl.EventPlayerTabOverlayDisplayName;
import gg.vape.friend.FriendAliasDisplayNameListener;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.world.cheststeal.ChestStealInventoryState;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.StringTextComponentBase;
import gg.vape.wrapper.impl.TextComponentBaseBridge;
import gg.vape.wrapper.impl.TextComponentString;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class EventNameFormatRewriteService
extends FriendAliasDisplayNameListener {
    private static String[] obfuscationState;

    private ITextComponent rewriteComponent(ITextComponent component) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.rewriteComponentTree(component);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            this.rewriteLegacyStringComponents(component);
            return component;
        }
        Vape.notifyNativeStackTrace();
        return component;
    }

    private Set<TextComponentString> collectStringComponents(ITextComponent component, Set<TextComponentString> components) {
        if (ForgeVersion.MC_1_16_5_ACTUAL.Y()) {
            throw new UnsupportedOperationException("getTextComponents should only be called on versions <= 1.16.5");
        }
        if (component.isInstance(MappedClasses.ux)) {
            ChestStealInventoryState translationComponent = new ChestStealInventoryState(component.getObject());
            for (Object siblingHandle : translationComponent.getSiblings()) {
                ITextComponent sibling = new ITextComponent(siblingHandle);
                if (sibling.isNull() || !sibling.isInstance(MappedClasses.Yr)) continue;
                this.collectStringComponents(sibling, components);
            }
        }
        for (ITextComponent sibling : component.G()) {
            TextComponentString stringComponent;
            if (sibling.isNull() || !sibling.isInstance(MappedClasses.z9)) continue;
            stringComponent = new TextComponentString(sibling.getObject());
            this.collectStringComponents(stringComponent, components);
        }
        if (component.isInstance(MappedClasses.z9)) {
            components.add(new TextComponentString(component.getObject()));
        }
        return components;
    }

    @EventHandler
    public void onNameFormat(EventNameFormat event) {
        ITextComponent rewrittenComponent;
        if (!(this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends())) {
            return;
        }
        ITextComponent originalComponent = event.getDisplayName();
        if (EventNameFormatRewriteService.shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
            event.setDisplayName(rewrittenComponent);
        }
    }

    static {
        EventNameFormatRewriteService.setObfuscationState(null);
    }

    @EventHandler
    public void onPlayerTabDisplayName(EventPlayerTabOverlayDisplayName event) {
        ITextComponent rewrittenComponent;
        if (!(this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends())) {
            return;
        }
        ITextComponent originalComponent = event.getDisplayName();
        if (EventNameFormatRewriteService.shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
            event.setDisplayName(rewrittenComponent);
        }
    }

    private static boolean shouldReplace(ITextComponent original, ITextComponent rewritten) {
        return original.isNotNull() && rewritten.isNotNull() && (ForgeVersion.MC_1_20_6.v() || !original.equals(rewritten));
    }

    public static String[] getObfuscationState() {
        return obfuscationState;
    }

    private StringTextComponentBase rewriteStringPayload(StringTextComponentBase payload) {
        ITextComponent rewrittenComponent;
        ITextComponent originalComponent;
        if (payload.isInstance(MappedClasses.qT) && !(originalComponent = new ScorePlayerTeamTextComponent(payload.getObject())).equals(rewrittenComponent = this.rewriteLiteralComponent((ScorePlayerTeamTextComponent)originalComponent))) {
            return new StringTextComponentBase(rewrittenComponent.getObject());
        }
        if (payload.isInstance(MappedClasses.ux) && !(originalComponent = new ChestStealInventoryState(payload.getObject())).equals(rewrittenComponent = this.rewriteTranslationComponent((ChestStealInventoryState)originalComponent))) {
            return new StringTextComponentBase(rewrittenComponent.getObject());
        }
        return payload;
    }

    @EventHandler
    public void onChat(EventChat event) {
        ITextComponent rewrittenComponent;
        if (ForgeVersion.MC_1_16_5_ACTUAL.Y() || !this.isAliasSpoofEnabled() || !this.hasFriends()) {
            return;
        }
        ITextComponent originalComponent = event.getMessage();
        if (EventNameFormatRewriteService.shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
            event.setMessage(rewrittenComponent);
        }
    }

    private ITextComponent rewriteComponentTree(ITextComponent component) {
        StringTextComponentBase rewrittenPayload;
        StringTextComponentBase originalPayload = component.F();
        boolean changed = !originalPayload.equals(rewrittenPayload = this.rewriteStringPayload(originalPayload));
        List<ITextComponent> siblings = component.G();
        ArrayList<ITextComponent> rewrittenSiblings = new ArrayList<ITextComponent>();
        for (ITextComponent sibling : siblings) {
            ITextComponent rewrittenSibling = this.rewriteComponent(sibling);
            rewrittenSiblings.add(rewrittenSibling);
            if (changed || rewrittenSibling.equals(sibling)) continue;
            changed = true;
        }
        return changed ? TextComponentBaseBridge.create(rewrittenPayload, rewrittenSiblings, component.J()) : component;
    }

    @EventHandler
    public void onChatMessageRender(EventChatMessageRender event) {
        ITextComponent rewrittenComponent;
        ITextComponent originalComponent = event.getContentComponent();
        if (EventNameFormatRewriteService.shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
            event.setOutputContentComponent(rewrittenComponent);
        }
    }

    private static UnsupportedOperationException identityException(UnsupportedOperationException exception) {
        return exception;
    }

    private ScorePlayerTeamTextComponent rewriteLiteralComponent(ScorePlayerTeamTextComponent component) {
        if (ForgeVersion.MC_1_16_5_ACTUAL.B()) {
            throw new UnsupportedOperationException("processStringTextComponent should only be called on versions >= 1.16.6");
        }
        if (component.isNull()) {
            return component;
        }
        String text = component.Y();
        if (text == null) {
            return component;
        }
        String rewrittenText = this.getReplacedDisplayName(text, this.getTargetedFriends());
        if (rewrittenText == null) {
            return component;
        }
        return text.equalsIgnoreCase(rewrittenText) ? component : (ForgeVersion.MC_1_20_6.d() ? ScorePlayerTeamTextComponent.P(rewrittenText) : ScorePlayerTeamTextComponent.B(rewrittenText));
    }

    private ChestStealInventoryState rewriteTranslationComponent(ChestStealInventoryState component) {
        Object[] formatArguments;
        if (ForgeVersion.MC_1_20_6.v()) {
            throw new UnsupportedOperationException("processChatComponentTranslation should only be called on versions >= 1.20.6");
        }
        boolean changed = false;
        ArrayList<Object> rewrittenArguments = new ArrayList<Object>();
        for (Object argument : formatArguments = component.getFormatArguments()) {
            Object rewrittenArgument;
            Object originalArgument;
            if (argument == null) {
                rewrittenArguments.add(null);
                continue;
            }
            if (MappedClasses.Yr.isInstance(argument)) {
                originalArgument = new ITextComponent(argument);
                rewrittenArgument = this.rewriteComponent((ITextComponent)originalArgument);
                rewrittenArguments.add(((Wrapper)rewrittenArgument).getObject());
                if (changed || ((Wrapper)originalArgument).equals(rewrittenArgument)) continue;
                changed = true;
                continue;
            }
            if (argument instanceof String) {
                originalArgument = (String)argument;
                rewrittenArgument = this.getReplacedDisplayName((String)originalArgument, this.getTargetedFriends());
                rewrittenArguments.add(rewrittenArgument);
                if (changed || ((String)originalArgument).equalsIgnoreCase((String)rewrittenArgument)) continue;
                changed = true;
                continue;
            }
            rewrittenArguments.add(argument);
        }
        return !changed ? component : (ForgeVersion.MC_1_20_6.d() ? ChestStealInventoryState.createTranslationWithFallback(component.getTranslationKey(), component.getFallback(), rewrittenArguments.toArray()) : ChestStealInventoryState.createTranslation(component.getTranslationKey(), rewrittenArguments.toArray()));
    }

    public static void setObfuscationState(String[] state) {
        obfuscationState = state;
    }

    @EventHandler
    public void onEntityRenderState(EventEntityRenderState event) {
        if (!(this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends())) {
            return;
        }
        ITextComponent displayName = event.getEntityRenderState().d();
        if (displayName.isNull()) {
            return;
        }
        ITextComponent rewrittenDisplayName = this.rewriteComponent(displayName);
        if (EventNameFormatRewriteService.shouldReplace(displayName, rewrittenDisplayName)) {
            event.getEntityRenderState().Z(rewrittenDisplayName);
        }
    }

    private void rewriteLegacyStringComponents(ITextComponent component) {
        Set<TextComponentString> stringComponents = this.collectStringComponents(component, new HashSet<TextComponentString>());
        for (TextComponentString stringComponent : stringComponents) {
            String text = stringComponent.getText();
            String rewrittenText = this.getReplacedDisplayName(text, this.getTargetedFriends());
            if (rewrittenText == null || text.equalsIgnoreCase(rewrittenText)) continue;
            stringComponent.setText(rewrittenText);
        }
    }
}
