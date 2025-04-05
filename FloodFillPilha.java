import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FloodFillPilha {
    private BufferedImage image;
    private Pilha pilha;
    private String outputFolder = "saidas/Pilha";
    private int pixelCounter = 0;
    private int snapshotInterval = 1000;
    private int boundaryColor = 0xFF000000; 

    public FloodFillPilha(BufferedImage image) {
        this.image = image;
        this.pilha = new Pilha();
        new File(outputFolder).mkdirs();
    }

    public void preencher(int x, int y, int novaCor) {
        int current = image.getRGB(x, y);
        if (colorsAreSimilar(current, boundaryColor) || colorsAreSimilar(current, novaCor))
            return;
        pilha.push(new Ponto(x, y));
        while (!pilha.isEmpty()) {
            Ponto p = pilha.pop();
            int px = p.x, py = p.y;
            if (px < 0 || py < 0 || px >= image.getWidth() || py >= image.getHeight())
                continue;
            int col = image.getRGB(px, py);
            if (colorsAreSimilar(col, boundaryColor) || colorsAreSimilar(col, novaCor))
                continue;
            image.setRGB(px, py, novaCor);
            pixelCounter++;
            if (pixelCounter % snapshotInterval == 0)
                saveSnapshot();
            pilha.push(new Ponto(px + 1, py));
            pilha.push(new Ponto(px - 1, py));
            pilha.push(new Ponto(px, py + 1));
            pilha.push(new Ponto(px, py - 1));
        }
    }

    private boolean colorsAreSimilar(int c1, int c2) {
        int r1 = (c1 >> 16) & 0xFF, g1 = (c1 >> 8) & 0xFF, b1 = c1 & 0xFF;
        int r2 = (c2 >> 16) & 0xFF, g2 = (c2 >> 8) & 0xFF, b2 = c2 & 0xFF;
        int tol = 10;
        return Math.abs(r1 - r2) < tol && Math.abs(g1 - g2) < tol && Math.abs(b1 - b2) < tol;
    }

    private void saveSnapshot() {
        try {
            File outFile = new File(outputFolder, "frame_" + pixelCounter + ".png");
            ImageIO.write(image, "png", outFile);
        } catch (IOException e) {
            System.out.println("Erro (Pilha): " + e.getMessage());
        }
    }
}
