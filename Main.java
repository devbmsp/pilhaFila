import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        try {
            File inputImageFile = new File("entrada.png");
            System.out.println("Caminho absoluto do arquivo: " + inputImageFile.getAbsolutePath());
            if (!inputImageFile.exists()) {
                System.out.println("Arquivo de entrada não encontrado!");
                return;
            }
            
            BufferedImage image = ImageIO.read(inputImageFile);
            int startX = 10, startY = 10;
            int newColor = java.awt.Color.YELLOW.getRGB();
            
            // Flood fill usando Pilha
            FloodFillPilha floodFillPilha = new FloodFillPilha(image);
            floodFillPilha.preencher(startX, startY, newColor);
            File outputPilha = new File("saidas/Pilha/saida_final.png");
            outputPilha.getParentFile().mkdirs();
            ImageIO.write(image, "png", outputPilha);
            
            // Recarrega a imagem original para o método com Fila
            image = ImageIO.read(inputImageFile);
            FloodFillFila floodFillFila = new FloodFillFila(image);
            floodFillFila.preencher(startX, startY, newColor);
            File outputFila = new File("saidas/Fila/saida_final.png");
            outputFila.getParentFile().mkdirs();
            ImageIO.write(image, "png", outputFila);
            
            System.out.println("Processo concluído! Verifique os arquivos de saída.");
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar ou salvar a imagem: " + e.getMessage());
        }
    }
}
