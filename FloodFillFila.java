import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FloodFillFila {
    private BufferedImage image;
    private Fila fila;
    private String outputFolder = "saidas/Fila";
    private int pixelCounter = 0;
    private int snapshotInterval = 500; // Salva snapshot a cada 100 pixels preenchidos
    private int boundaryColor = 0xFF000000; // Cor de contorno: preto

    public FloodFillFila(BufferedImage image) {
        this.image = image;
        this.fila = new Fila();
        new File(outputFolder).mkdirs();
    }

    public void preencher(int x, int y, int novaCor) {
        int current = image.getRGB(x, y);
        if (colorsAreSimilar(current, boundaryColor) || colorsAreSimilar(current, novaCor))
            return;
        fila.enqueue(new Ponto(x, y));
        while (!fila.isEmpty()) {
            Ponto p = fila.dequeue();
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
            fila.enqueue(new Ponto(px + 1, py));
            fila.enqueue(new Ponto(px - 1, py));
            fila.enqueue(new Ponto(px, py + 1));
            fila.enqueue(new Ponto(px, py - 1));
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
            System.out.println("Erro (Fila): " + e.getMessage());
        }
    }
}
