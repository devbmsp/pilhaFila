import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
            
            FloodFillPilha floodFillPilha = new FloodFillPilha(image);
            floodFillPilha.preencher(startX, startY, newColor);
            File outputPilha = new File("saidas/Pilha/saida_final.png");
            outputPilha.getParentFile().mkdirs();
            ImageIO.write(image, "png", outputPilha);
            
            image = ImageIO.read(inputImageFile);
            FloodFillFila floodFillFila = new FloodFillFila(image);
            floodFillFila.preencher(startX, startY, newColor);
            File outputFila = new File("saidas/Fila/saida_final.png");
            outputFila.getParentFile().mkdirs();
            ImageIO.write(image, "png", outputFila);
            
            System.out.println("Processo concluído! Verifique os arquivos de saída.");
            
            createVideo("saidas/Pilha", "saidas/video_pilha.mp4");
            createVideo("saidas/Fila", "saidas/video_fila.mp4");
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar ou salvar a imagem: " + e.getMessage());
        }
    }
    
    private static void createVideo(String inputFolder, String outputVideo) {

        String comando = "ffmpeg -r 30 -pattern_type glob -i '" + inputFolder + "/*.png' -c:v libx264 -pix_fmt yuv420p " + outputVideo;
        
        try {
            ProcessBuilder pb = new ProcessBuilder("sh", "-c", comando);
            pb.redirectErrorStream(true); 
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Vídeo criado com sucesso: " + outputVideo);
            } else {
                System.out.println("Erro ao criar vídeo (" + outputVideo + "). Código de saída: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
