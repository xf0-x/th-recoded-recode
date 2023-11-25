package thunder.hack.gui.hud.impl;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Formatting;
import thunder.hack.ThunderHack;
import thunder.hack.core.impl.ModuleManager;
import thunder.hack.gui.font.FontRenderers;
import thunder.hack.gui.hud.HudElement;
import thunder.hack.modules.client.HudEditor;
import thunder.hack.modules.client.Media;
import thunder.hack.setting.Setting;
import thunder.hack.utility.render.Render2DEngine;
import thunder.hack.utility.render.TextUtil;

import static thunder.hack.core.impl.ServerManager.getPing;

public class WaterMark extends HudElement {
    public WaterMark() {
        super("WaterMark", 100, 35);
    }

    public static final Setting<Mode> mode = new Setting<>("Mode", Mode.Big);
    private final Setting<Boolean> ru = new Setting<>("RU", false);

    private final TextUtil textUtil = new TextUtil(
                    "ТандерХак",
                    "ГромХак",
                    "ГрозаКлиент",
                    "ТандерХуй",
                    "ТандерХряк",
                    "ТандерХрюк",
                    "ТиндерХак",
                    "ТундраХак",
                    "ГромВзлом"
    );


    private enum Mode {
        Big, Small, Classic
    }

    public void onRender2D(DrawContext context) {
        super.onRender2D(context);
        String username = ((ModuleManager.media.isEnabled() && Media.nickProtect.getValue()) || ModuleManager.nameProtect.isEnabled()) ? (ModuleManager.nameProtect.isEnabled() ? ModuleManager.nameProtect.getName() : "Protected") : mc.getSession().getUsername();

        if (mode.getValue() == Mode.Big) {
            FontRenderers.thglitch.drawString(context.getMatrices(), "THUNDERHACK", getPosX() + 5.5, getPosY() + 5, -1);
            FontRenderers.monsterrat.drawGradientString(context.getMatrices(), "recode", getPosX() + 35.5f, getPosY() + 21f, 1, true);
        } else if (mode.getValue() == Mode.Small) {
            String info = Formatting.DARK_GRAY+ "| " + Formatting.RESET + username + Formatting.DARK_GRAY + " | " + Formatting.RESET + getPing() + " ms" + Formatting.DARK_GRAY + " | " + Formatting.RESET + (mc.isInSingleplayer() ? "SinglePlayer" : mc.getNetworkHandler().getServerInfo().address);
            FontRenderers.sf_bold.drawGradientString(context.getMatrices(), ru.getValue() ? textUtil + " " : "ThunderHack ", getPosX() + 2, getPosY() + 2.5f, 10, true);
            FontRenderers.sf_bold.drawString(context.getMatrices(), info, getPosX() + 2 + FontRenderers.sf_bold.getStringWidth("ThunderHack "), getPosY() + 2.5f, HudEditor.textColor.getValue().getColor());
        } else {
            FontRenderers.monsterrat.drawGradientString(context.getMatrices(), "ThunderHack v" + ThunderHack.VERSION, getPosX() + 5.5f, getPosY() + 5, 10, true);
        }
    }

    public void onRenderShaders(DrawContext context) {
        String username = ((ModuleManager.media.isEnabled() && Media.nickProtect.getValue()) || ModuleManager.nameProtect.isEnabled()) ? (ModuleManager.nameProtect.isEnabled() ? ModuleManager.nameProtect.getName() : "Protected") : mc.getSession().getUsername();

        if (mode.getValue() == Mode.Big) {
            Render2DEngine.drawGradientGlow(context.getMatrices(), HudEditor.getColor(270), HudEditor.getColor(0), HudEditor.getColor(180), HudEditor.getColor(90), getPosX(), getPosY(), 106, 30, HudEditor.hudRound.getValue(), 10);
            Render2DEngine.drawGradientRoundShader(context.getMatrices(), HudEditor.getColor(270), HudEditor.getColor(0), HudEditor.getColor(180), HudEditor.getColor(90), getPosX() - 0.5f, getPosY() - 0.5f, 107, 31, HudEditor.hudRound.getValue());
            Render2DEngine.drawRoundShader(context.getMatrices(), getPosX(), getPosY(), 106, 30, HudEditor.hudRound.getValue(), HudEditor.plateColor.getValue().getColorObject());
        } else if (mode.getValue() == Mode.Small) {
            String info = Formatting.DARK_GRAY + "| " + Formatting.RESET + username + Formatting.DARK_GRAY + " | " + Formatting.RESET + getPing() + " ms" + Formatting.DARK_GRAY + " | " + Formatting.RESET + (mc.isInSingleplayer() ? "SinglePlayer" : mc.getNetworkHandler().getServerInfo().address);
            Render2DEngine.drawGradientGlow(context.getMatrices(), HudEditor.getColor(270), HudEditor.getColor(0), HudEditor.getColor(180), HudEditor.getColor(90), getPosX(), getPosY(), FontRenderers.sf_bold.getStringWidth("ThunderHack " + info) + 5, 10, 3, 10);
            Render2DEngine.drawGradientRoundShader(context.getMatrices(), HudEditor.getColor(270), HudEditor.getColor(0), HudEditor.getColor(180), HudEditor.getColor(90), getPosX() - 1f, getPosY() - 1f, FontRenderers.sf_bold.getStringWidth("ThunderHack " + info) + 7, 12, 3);
            Render2DEngine.drawRoundShader(context.getMatrices(), getPosX() - 0.5f, getPosY() - 0.5f, FontRenderers.sf_bold.getStringWidth("ThunderHack " + info) + 6, 11, 3, HudEditor.plateColor.getValue().getColorObject());
        }
    }

    @Override
    public void onUpdate() {
        textUtil.tick();
    }
}