package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.nio.file.Path;

public class CoordinatesManagerScreen extends Screen {
    private CoordinateManagerWidget coordinateManagerWidget;

    private final Path filepath;

    private MCGButtonWidget newCoordinate;
    private MCGButtonWidget removeCoordinate;
    private MCGButtonWidget teleportToCoordinate;

    public CoordinatesManagerScreen(Path filepath) {
        super(new LiteralText("CoordinateManagerScreen"));
        this.filepath = filepath;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        coordinateManagerWidget = new CoordinateManagerWidget(client, width / 3 * 2, height - 60, 40, this.height - 20, 15, 10);
        coordinateManagerWidget.setFile(filepath);
        this.addChild(coordinateManagerWidget);
        newCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, coordinateManagerWidget.getTop(), coordinateManagerWidget.getButtonWidth(), 30, new LiteralText("New Coordinate"), press -> {
            coordinateManagerWidget.newCoordinate(this);
        });
        this.addButton(newCoordinate);
        removeCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, newCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 30, new LiteralText("Remove Coordinate"), press -> {
            coordinateManagerWidget.removeCoordinate();
        });
        this.addButton(removeCoordinate);
        teleportToCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, removeCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 30, new LiteralText("TP to Selected"), press -> {
            coordinateManagerWidget.teleportToCoordinate();
        });
        this.addButton(teleportToCoordinate);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        this.drawCenteredString(matrices, textRenderer, "Coordinates of " + filepath.getFileName().toString().replace(".coordinates", ""), width / 2, 10, 16777215);
        coordinateManagerWidget.render(matrices, mouseX, mouseY, delta);

        // selected coordinate view
        if (coordinateManagerWidget.getSelected() != null) {
            CoordinatesSet set = ((CoordinateManagerWidget.CoordinateEntry) coordinateManagerWidget.getSelected()).coordinate;
            int drawY = teleportToCoordinate.getBottom() + 20;
            this.drawStringWithShadow(matrices, textRenderer, set.name, coordinateManagerWidget.getRight() + 5, drawY, 16777215);
            this.drawStringWithShadow(matrices, textRenderer, Integer.toString(set.x), coordinateManagerWidget.getRight() + 5, drawY + 15, 16777215);
            this.drawStringWithShadow(matrices, textRenderer, Integer.toString(set.y), coordinateManagerWidget.getRight() + 5, drawY + 25, 16777215);
            this.drawStringWithShadow(matrices, textRenderer, Integer.toString(set.z), coordinateManagerWidget.getRight() + 5, drawY + 35, 16777215);
            this.drawStringWithShadow(matrices, textRenderer, set.description, coordinateManagerWidget.getRight() + 5, drawY + 50, 16777215);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void refresh() {
        coordinateManagerWidget.refreshEntries();
    }

    public void updateButtonStates() {
        removeCoordinate.active = coordinateManagerWidget.getSelected() != null;
        teleportToCoordinate.active = coordinateManagerWidget.getSelected() != null && client.player.isCreative();
    }

    public Path getFilepath() {
        return filepath;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}