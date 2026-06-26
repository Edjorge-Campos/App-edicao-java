import pt.iscte.greyditor.Greyditor;
import pt.iscte.greyditor.Editor;
import pt.iscte.greyditor.Selection;
    void main() {
        Greyditor configuration = new Greyditor("Editor de foto");
        configuration.addFilter("inverter", this::inverter);
        configuration.addFilter("escurecer", this::escurecer, 0, 255);
        configuration.addFilter("clarear", this::clarear, 0, 255);
        configuration.addFilter("contraste", this::contraste, 0, 255);
        configuration.addEffect("vignette", this::vignette, 0, 255);
        configuration.addEffect("margem", this::margem, 0, 50);
        configuration.addFilter("grão", this::grao, 0, 255);
        configuration.addEffect("espelhar horizontalmente", this::espelharHorizontalmente);
        configuration.addEffect("espelhar verticalmente", this::espelharVerticalmente);
        configuration.addEffect("Grid", this::grid);
        configuration.addEffect("linhas", this::lines, 0, 50);
        configuration.addOperation("Square", this::square);
        configuration.addOperation("escurecer área", this::escolherArea);
        configuration.addOperation("crop", this::crop);
        configuration.addOperation("expandir", this::expandir);
        configuration.addOperation("rodar", this::rodar);
        configuration.addEffect("antigo", this::antigo);
        configuration.addOperation("desfocar", this::desfocar);
        configuration.addOperation("Posterizar", this::posterizar);
        configuration.addEffect("retro", this::retro);
       // configuration.addSaveOperation("Save");
       // configuration.addLoadOperation("Load");
        configuration.addOperation("copiar", this::copiar);
        configuration.addOperation("cortar", this::cortar);
        configuration.addOperation("colar", this::colar);
        configuration.open("monalisa.jpg");
    }
    int[][] memória = null;

        int inverter(int tom) {
            return 255 - tom;
        }

        int escurecer(int tom, int intensidade) {
            return Math.max(0, tom - intensidade);
        }

        int clarear(int tom, int intensidade) {
            return Math.min(255, tom + intensidade);
        }

        int contraste(int tom, int intensidade) {
            if (tom >= 127) {
                return clarear(tom, intensidade);
            } // se o tom for mais branco que preto escurecemos, se for mais preto que branco clareamos
            else {
                return escurecer(tom, intensidade);
            }
        }              // estava a dar erro que ficava tudo vermelho, porque eu nao dizia que o amximo era 255

        void espelharHorizontalmente(int[][] imagem) {
            int altura = imagem.length;
            int comprimento = imagem[0].length;
            for (int y = 0; y < comprimento / 2; y++) {  // assegurar que estamos a trabalhar na primeira metade da imagem
                for (int x = 0; x < altura; x++) {
                    int varTemp = imagem[x][y]; //para guardar a primeira parte da imagem que vai ser posta no outro lado
                    imagem[x][y] = imagem[x][comprimento - 1 - y];
                    imagem[x][comprimento - 1 - y] = varTemp;
                }
            }
        }

        void espelharVerticalmente(int[][] imagem) {
            int comprimento = imagem.length;
            int altura = imagem[0].length;
            for (int y = 0; y < comprimento / 2; y++) { //assegurar que estamos a trabalahr na primeira parte da imagem pensando na altura
                for (int x = 0; x < altura; x++) {
                    int varTemp = imagem[y][x];
                    imagem[y][x] = imagem[comprimento - 1 - y][x];
                    imagem[comprimento - 1 - y][x] = varTemp;
                }
            }
        }

        void vignette(int[][] imagem, int intensidade) {
            int comprimento = imagem.length;
            int altura = imagem[0].length;
            int cx = comprimento / 2;
            int cy = altura / 2; //encontrar o centro da imagem
            double maxDistancia = Math.hypot(cx, cy); //ver qual a distância entre o centro e os cantos da imagem
            double forca = intensidade / 100.0;
            for (int x = 0; x < comprimento; x++) {
                for (int y = 0; y < altura; y++) {
                    double distancia = Math.hypot(x - cx, y - cy);
                    double fator = 1.0 - ((distancia / maxDistancia) * forca); //fazer ficar gradual
                    if (fator < 0) {
                        fator = 0;
                    }
                    imagem[x][y] = (int) (imagem[x][y] * fator);
                }
            }
        }

        void grid(int[][] imagem) {
            int hspace = (imagem[0].length + 2) / 3;
            int vspace = (imagem.length + 2) / 3;
            for (int y = vspace; y < imagem.length; y += vspace)
                for (int x = 0; x < imagem[y].length; x++)
                    imagem[y][x] = 200;
            for (int x = hspace; x < imagem[0].length; x += hspace)
                for (int y = 0; y < imagem.length; y++)
                    imagem[y][x] = 200;
        }

        void lines(int[][] imagem, int espacamento) {
            if (espacamento == 0)
                return;
            for (int y = 0; y < imagem.length; y += espacamento)
                for (int x = 0; x < imagem[y].length; x++)
                    imagem[y][x] = 0;
        }

        int grao(int tom, int intensidade) {
            double a = Math.random();
            if (a <= 0.5) {
                return clarear(tom, intensidade);
            } else return escurecer(tom, intensidade);
        }

        void margem(int[][] imagem, int tamanho) {
            int comprimento = imagem.length;
            int altura = imagem[0].length;
            for (int x = 0; x < altura; x++) {
                for (int y = 0; y < comprimento; y++) {
                    imagem[0][x] = 0;
                    imagem[comprimento - 1][x] = 0;
                    imagem[y][altura - 1] = 0;
                    imagem[y][0] = 0;
                    if (y < tamanho || y >= comprimento - tamanho || x < tamanho || x >= altura - tamanho) {
                        if (x == tamanho - 1 || x == altura - tamanho || y == tamanho - 1 || y == comprimento - tamanho) {
                            imagem[y][x] = 0; // verifica se estamos 1 antes da imagem começar
                        }
                        imagem[y][x] = 255;
                    }
                }
            }
        }

        int[][] square(int[][] imagem) {
            int lado = Math.min(imagem.length, imagem[0].length);
            int[][] quadrado = new int[lado][lado];
            for (int y = 0; y < lado; y++)
                for (int x = 0; x < lado; x++)
                    quadrado[y][x] = imagem[y][x];

            return quadrado;
        }

        int[][] escolherArea(int[][] imagem, Editor editor) {
            Selection selection = editor.getSelection();
            if (selection == null) {
                editor.message("Escoha uma área para escurecer.");
            } else {
                int fator = editor.getInteger("Intensidade?");
                for (int y = selection.y(); y < selection.y() + selection.height(); y++)
                    for (int x = selection.x(); x < selection.x() + selection.width(); x++)
                        imagem[y][x] = Math.max(0, imagem[y][x] - fator);
            }
            return null;
        }

        int[][] crop(int[][] imagem, Editor editor) {
            Selection selection = editor.getSelection();
            if (selection == null) { //impedir que dê erro se a pessoa nao escolher nada
                editor.message("Por favor escoha a área que quer cortar.");
                return imagem;
            }
            int[][] cort = new int[selection.height()][selection.width()];
            for (int x = 0; x < selection.width(); x++) {
                for (int y = 0; y < selection.height(); y++) {
                    cort[y][x] = imagem[selection.y() + y][selection.x() + x]; //apartir da seleção continuar ate ao y/x para fazer a imagem
                }
            }
            return cort;
        }

        int[][] expandir(int[][] imagem, Editor editor) {
            int tamanho = editor.getInteger("Qual o novo tamanho?"); //como nao estava a funcionar por o tamanho no função em si por fora
            int comprimento = imagem.length;
            int altura = imagem[0].length;
            int[][] imagemmaior = new int[tamanho][tamanho];
            if (tamanho < comprimento || tamanho < altura) {
                return imagem; // garantir que apenas aumenta e nunca diminui a imagem
            }
            for (int i = 0; i < comprimento; i++) {
                for (int j = 0; j < altura; j++) {
                    if (i + (tamanho - comprimento) / 2 >= 0 && i + (tamanho - comprimento) / 2 < tamanho && j + (tamanho - altura) / 2 >= 0 && j + (tamanho - altura) / 2 < tamanho) { // verificar que os índices não saem fora dos limites da nova imagem
                        imagemmaior[i + (tamanho - comprimento) / 2][j + (tamanho - altura) / 2] = imagem[i][j];
                    }
                }
            }
            return imagemmaior;
        }

        int[][] rodar(int[][] imagem) {
            int[][] nova = new int[imagem[0].length][imagem.length]; //inverter altura e comprimento porque vamos rodar a imagem
            for (int i = 0; i < imagem.length; i++) {
                for (int j = 0; j < imagem[0].length; j++) {
                    nova[imagem[0].length - 1 - j][i] = imagem[i][j]; //again inverter as coordenada para virar 90º
                }
            }
            return nova;
        }

        void antigo(int[][] imagem) {
            for (int x = 0; x < imagem.length; x++) {
                for (int y = 0; y < imagem[0].length; y++) {
                    imagem[x][y] = grao(imagem[x][y], 40);
                }
            }
            vignette(imagem, 100);
            margem(imagem, 20);
        }

        int[][] desfocar(int[][] imagem, Editor editor) {
            int raio = editor.getInteger("Qual o raio que quer que seja afetado?");
            return desfocando(imagem, raio);
        }

        int[][] desfocando(int[][] image, int raio) {
            int[][] novaImagem = new int[image.length][image[0].length];
            for (int y = 0; y < image.length; y++) {
                for (int x = 0; x < image[y].length; x++) {
                    int soma = 0;
                    int pixeis = 0;
                    for (int i = y - raio; i <= y + raio; i++) {
                        for (int j = x - raio; j <= x + raio; j++) {
                            if ((i >= 0 && i < image.length) && (j >= 0 && j < image[0].length)) {
                                soma += image[i][j];
                                pixeis++;
                            }
                        }
                    }
                    novaImagem[y][x] = soma / pixeis;
                }
            }
            return novaImagem;
        }

        int[][] posterizar(int[][] imagem, Editor editor) {
            int niveis = editor.getInteger("qual o nivel?");
            int intervalo = 256 / niveis;
            for (int x = 0; x < imagem.length; x++) {
                for (int y = 0; y < imagem[0].length; y++) {
                    int original = imagem[x][y];
                    int novaCor = (original / intervalo) * intervalo;
                    if (novaCor > 255) novaCor = 255;
                    imagem[x][y] = novaCor;
                }
            }
            return imagem;
        }

        int[][] retro(int[][] imagem) {
            for (int i = 0; i < imagem.length; i++) {
                for (int j = 0; j < imagem[0].length; j++) {
                    imagem[i][j] = contraste(imagem[i][j], 70);
                }
            }
            vignette(imagem, 70);
            imagem = desfocando(imagem, 3);
            return imagem;
        }

        int[][] copiar(int[][] imagem, Editor editor) {
            Selection selection = editor.getSelection();
            if (selection == null) {
                return imagem;
            }
            memória = new int[selection.width()][selection.height()];
            for (int i = 0; i < selection.width(); i++) {
                for (int j = 0; j < selection.height(); j++) {
                    memória[i][j] = imagem[selection.y() + j][selection.x() + i];
                }
            }
            return imagem;
        }

        int[][] cortar(int[][] imagem, Editor editor) {
            Selection selection = editor.getSelection();
            if (selection == null) {
                return imagem;
            }
            memória = new int[selection.width()][selection.height()];
            for (int i = 0; i < selection.width(); i++) {
                for (int j = 0; j < selection.height(); j++) {
                    memória[i][j] = imagem[selection.y() + j][selection.x() + i];
                    imagem[selection.y() + j][selection.x() + i] = 255;
                }
            }
            return imagem;
        }

        int[][] colar(int[][] imagem, Editor editor) {
            Selection selection = editor.getSelection();
            if (memória == null) {
                return imagem;
            }
            if (selection == null) {
                return imagem;
            }
            for (int x = 0; x < memória.length; x++) {
                for (int y = 0; y < memória[0].length; y++) {
                    if (selection.y() + y < imagem.length && selection.x() + x < imagem[0].length) { //para não sair fora da imagem
                        imagem[selection.y() + y][selection.x() + x] = memória[x][y];
                    }
                }
            }
            return imagem;
        }
