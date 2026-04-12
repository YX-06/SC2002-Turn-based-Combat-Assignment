
import boundary.BattleUI;
import boundary.GameMenuUI;
import boundary.GameOverUI;
import control.BattleEngine;
import control.GameSetupController;
import entity.Level;
import entity.combat.Player;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GameMenuUI menuUI = new GameMenuUI(scanner);
        GameOverUI gameOverUI = new GameOverUI(scanner);
        BattleUI battleUI = new BattleUI(scanner);
        GameSetupController setup = new GameSetupController();

        boolean running = true;
        boolean replaySameSettings = false;

        int classChoice = 0;
        int itemChoice1 = 0;
        int itemChoice2 = 0;
        int difficulty = 0;

        while (running) {
            if (!replaySameSettings) {
                menuUI.displayWelcome();
                menuUI.displayPlayerClasses();
                classChoice = menuUI.promptPlayerClass();

                menuUI.displayItemList();
                itemChoice1 = menuUI.promptItem(1);
                itemChoice2 = menuUI.promptItem(2);

                menuUI.displayDifficultyLevels();
                difficulty = menuUI.promptDifficulty();
            }

            Player player = setup.createPlayer(classChoice);
            player.addItem(setup.createItem(itemChoice1));
            player.addItem(setup.createItem(itemChoice2));

            Level level = setup.createLevel(difficulty);

            BattleEngine engine = new BattleEngine(player, level, battleUI);
            boolean won = engine.startBattle();

            if (won) {
                gameOverUI.showVictoryScreen(player, engine.getRoundNo());
            } else {
                gameOverUI.showDefeatScreen(player, level.getAllEnemies(), engine.getRoundNo());
            }

            int postGameChoice = gameOverUI.promptPostGameChoice();

            if (postGameChoice == 1) {
                replaySameSettings = true;
            } else if (postGameChoice == 2) {
                replaySameSettings = false;
            } else {
                running = false;
            }
        }

        scanner.close();
    }
}
