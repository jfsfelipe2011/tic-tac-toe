import javax.imageio.ImageIO;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable, MouseListener {
    public static int WIDTH = 300;
    public static int HEIGHT = 300;
    public final int PLAYER = 1, OPONENTE = -1;
    public int CURRENT = OPONENTE;
    public final int[][] TABULEIRO = new int [3][3];
    public BufferedImage PLAYER_SPRITE, OPONENTE_SPRITE;
    public int mx, my;
    private boolean pressed = false;


    public Game() throws IOException {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addMouseListener(this);
        PLAYER_SPRITE = ImageIO.read(getClass().getResource("/player.png"));
        OPONENTE_SPRITE = ImageIO.read(getClass().getResource("/oponente.png"));
        resetTabuleiro();
    }

    public void resetTabuleiro() {
        for (int xx = 0; xx < TABULEIRO.length; xx++) {
            for (int yy = 0; yy < TABULEIRO.length; yy++) {
                TABULEIRO[xx][yy] = 0;
            }
        }
    }

    public int checkVictory() {
        // Horizontal
        if (TABULEIRO[0][0] == PLAYER && TABULEIRO[1][0] == PLAYER && TABULEIRO[2][0] == PLAYER) {
            return PLAYER;
        }

        if (TABULEIRO[0][1] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[2][1] == PLAYER) {
            return PLAYER;
        }

        if (TABULEIRO[0][2] == PLAYER && TABULEIRO[1][2] == PLAYER && TABULEIRO[2][2] == PLAYER) {
            return PLAYER;
        }

        // Vertical
        if (TABULEIRO[0][0] == PLAYER && TABULEIRO[0][1] == PLAYER && TABULEIRO[0][2] == PLAYER) {
            return PLAYER;
        }

        if (TABULEIRO[1][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[1][2] == PLAYER) {
            return PLAYER;
        }

        if (TABULEIRO[2][0] == PLAYER && TABULEIRO[2][1] == PLAYER && TABULEIRO[2][2] == PLAYER) {
            return PLAYER;
        }

        // Diagonal
        if (TABULEIRO[0][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[2][2] == PLAYER) {
            return PLAYER;
        }

        if (TABULEIRO[2][0] == PLAYER && TABULEIRO[1][1] == PLAYER && TABULEIRO[0][2] == PLAYER) {
            return PLAYER;
        }


        // Horizontal
        if (TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][0] == OPONENTE && TABULEIRO[2][0] == OPONENTE) {
            return OPONENTE;
        }

        if (TABULEIRO[0][1] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[2][1] == OPONENTE) {
            return OPONENTE;
        }

        if (TABULEIRO[0][2] == OPONENTE && TABULEIRO[1][2] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
            return OPONENTE;
        }

        // Vertical
        if (TABULEIRO[0][0] == OPONENTE && TABULEIRO[0][1] == OPONENTE && TABULEIRO[0][2] == OPONENTE) {
            return OPONENTE;
        }

        if (TABULEIRO[1][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[1][2] == OPONENTE) {
            return OPONENTE;
        }

        if (TABULEIRO[2][0] == OPONENTE && TABULEIRO[2][1] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
            return OPONENTE;
        }

        // Diagonal
        if (TABULEIRO[0][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[2][2] == OPONENTE) {
            return OPONENTE;
        }

        if (TABULEIRO[2][0] == OPONENTE && TABULEIRO[1][1] == OPONENTE && TABULEIRO[0][2] == OPONENTE) {
            return OPONENTE;
        }

        int curScore = 0;

        for (int xx = 0; xx < TABULEIRO.length; xx++) {
            for (int yy = 0; yy < TABULEIRO.length; yy++) {
                if (TABULEIRO[xx][yy] != 0) {
                    curScore++;
                }
            }
        }

        if (curScore == TABULEIRO.length * TABULEIRO[0].length) {
            return 0;
        }

        return -10;
    }

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setTitle("Tic Toc Toe");
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        new Thread(game).start();
    }

    public void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();

        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (int xx = 0; xx < TABULEIRO.length; xx++) {
            for (int yy = 0; yy < TABULEIRO.length; yy++) {
                g.setColor(Color.BLACK);
                g.drawRect(xx * 100, yy * 100, 100, 100);

                if (TABULEIRO[xx][yy] == PLAYER) {
                    g.drawImage(PLAYER_SPRITE, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                } else if (TABULEIRO[xx][yy] == OPONENTE) {
                    g.drawImage(OPONENTE_SPRITE, xx * 100 + 25, yy * 100 + 25, 50, 50, null);
                }
            }
        }
        
        g.dispose();
        bufferStrategy.show();
    }

    public void tick() {
        if (CURRENT == PLAYER) {
            if (pressed) {
                pressed = false;

                mx/=100;
                my/=100;

                if (TABULEIRO[mx][my] == 0) {
                    TABULEIRO[mx][my] = PLAYER;
                    CURRENT = OPONENTE;
                }
            }
        } else if (CURRENT == OPONENTE) {
            // Multiplayer
            /*if (pressed) {
                pressed = false;

                mx/=100;
                my/=100;

                if (TABULEIRO[mx][my] == 0) {
                    TABULEIRO[mx][my] = OPONENTE;
                    CURRENT = PLAYER;
                }
            }*/
            for (int xx = 0; xx < TABULEIRO.length; xx++) {
                for (int yy = 0; yy < TABULEIRO.length; yy++) {
                    if (TABULEIRO[xx][yy] == 0) {
                        Node bestMove = getBestMove(xx, yy, 0, OPONENTE);

                        TABULEIRO[bestMove.x][bestMove.y] = OPONENTE;
                        CURRENT = PLAYER;
                        return;
                    }
                }
            }
        }

        if (checkVictory() == PLAYER) {
            System.out.println("player ganhou");
            resetTabuleiro();
        } else if (checkVictory() == OPONENTE) {
            System.out.println("oponente ganhou");
            resetTabuleiro();
        } else if (checkVictory() == 0) {
            System.out.println("empate");
            resetTabuleiro();
        }
    }

    public Node getBestMove(int x, int y, int depth, int turno) {
        if (checkVictory() == PLAYER) {
            return new Node(x, y, depth - 10, depth);
        } else if (checkVictory() == OPONENTE) {
            return new Node(x, y, 10 - depth, depth);
        } else if (checkVictory() == 0) {
            return new Node(x, y, 0, depth);
        }

        List<Node> nodes = new ArrayList<Node>();

        for (int xx = 0; xx < TABULEIRO.length; xx++) {
            for (int yy = 0; yy < TABULEIRO.length; yy++) {
                Node node;

                if (TABULEIRO[xx][yy] == 0) {
                    if (turno == PLAYER) {
                        TABULEIRO[xx][yy] = PLAYER;
                        node = getBestMove(xx, yy, depth + 1, OPONENTE);
                        TABULEIRO[xx][yy] = 0;
                    } else {
                        TABULEIRO[xx][yy] = OPONENTE;
                        node = getBestMove(xx, yy, depth + 1, PLAYER);
                        TABULEIRO[xx][yy] = 0;
                    }
                    nodes.add(node);
                }
            }
        }

        Node finalNode = nodes.get(0);

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);

            if (turno == PLAYER) {
                if(n.score > finalNode.score) {
                    finalNode = n;
                }
            } else {
                if(n.score < finalNode.score) {
                    finalNode = n;
                }
            }
        }

        return finalNode;
    }

    @Override
    public void run() {
        double fps = 60.0;
        while (true) {
            tick();
            render();
            try {
                Thread.sleep((int) (1000/fps));
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        mx = e.getX();
        my = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
