import java.util.*;

class NaveInfo{
    int x0;
    int y0;
    char direction;

    void setX0(int x){
        this.x0 = x;
    }

    void setY0(int y){
        this.y0 = y;
    }

    void setDirection(String direction){
        this.direction = direction.charAt(1);
    }
}

class Direction{
    String n;
    String e;
    String s;
    String w;

    String getDirection(char direction){
        switch(direction){
            case 'n':
                this.n = "nwse"; 

                break;
            
            case 'e':

                this.e = "enws";
                
                break;

            case 's':
                this.s = "senw";

                break;

            case 'w':
                this.w = "wsen";

                break;
        }
        
        if(direction == 'n'){
            return this.n;
        }else if(direction == 'e'){
            return this.e;
        }else if(direction == 's'){
            return this.s;
        }else{
            return this.w;
        }
    }

    int[] getCordinates(char direction, int x, int y){
        int[] coordenadas;
        coordenadas = new int[2]; 
        if(direction == 'n'){
            coordenadas[0] = x;
            coordenadas[1] = y + 1;
        }else if(direction == 'e'){
            coordenadas[0] = x + 1;
            coordenadas[1] = y;
        }else if(direction == 's'){
            coordenadas[0] = x;
            coordenadas[1] = y -1;
        }else{
            coordenadas[0] = x - 1;
            coordenadas[1] = y;
        }

        return coordenadas;
    }
}

class Exploration{
    Direction exploracao = new Direction();
    ErrorTreatment erro = new ErrorTreatment();
    String moves;
    int height;
    int width;


    void setHeight(int height){
        this.height = height;
    }

    void setWidth(int width){
        this.width = width;
    }

    void setMoves(String moves){
        this.moves = moves;
    }

    String rotation(char direction){
        Direction direcao = new Direction();
        return direcao.getDirection(direction);
    }

    String finalPosition(char direction, int x0, int y0, int height, int width){
        int x = x0;
        int y = y0;
        char finalDirection = direction;
        String inicialRotation = this.rotation(direction);

        int qtL = 0;
        int qtR = 0;
        int difference = 0;

        for(int i = 0; i < this.moves.length(); i++){
            if(moves.charAt(i) == 'L'){
                qtL++;
                difference = qtL - qtR;

                if(difference >= 0){
                    finalDirection = inicialRotation.charAt(difference % 4);
                }else{
                    finalDirection = inicialRotation.charAt((4 + difference) % 4);
                }
            }else if(moves.charAt(i) == 'R'){
                qtR++;
                difference = qtL - qtR;

                if(difference >= 0){
                    finalDirection = inicialRotation.charAt(difference % 4);
                }else{
                    finalDirection = inicialRotation.charAt((4 + difference) % 4);
                }
            }else if(moves.charAt(i) == 'M'){
                erro.treatInicialPosition(x, y, height, width);
                x = exploracao.getCordinates(finalDirection, x, y)[0];
                y = exploracao.getCordinates(finalDirection, x, y)[1];

            }
        }

        return x + " " + y + " " + finalDirection;

    }
}

class ErrorTreatment{
    void treatInicialPosition(int x, int y, int height, int width){
        if(x > width || y > height || x < 0 || y < 0){
            System.out.print("\n===================================================================================\n");
            System.out.print("Parece que a nave nem Marte está explorando mais! Acabou saindo do perímetro :(\n");
            System.out.print("=====================================================================================\n");
        }

        if(height <= 0 || width <= 0){
            System.out.print("\n==========================================================================================\n");
            System.out.print("Parece que algum buraco negro destruiu as dimensões de Marte! São inválidas ou nulas :(\n");
            System.out.print("============================================================================================\n");

        }


    }
}

public class HelloWorld {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        ArrayList<NaveInfo> nave = new ArrayList<NaveInfo>();
        ArrayList<Exploration> explorando = new ArrayList<Exploration>();
        
        
        do{
            NaveInfo navezinha = new NaveInfo();
            Exploration explorar = new Exploration();
            System.out.print("Digite as dimensões do terreno: (separadas por espaço)\n");
            int height = s.nextInt();
            explorar.setHeight(height);
            int width = s.nextInt();
            explorar.setWidth(width);
            
            System.out.print("Digite a posição inicial da nave e a direção em que está (N, E, S, W):\n");
            int x = s.nextInt();
            navezinha.setX0(x);
            int y = s.nextInt();
            navezinha.setY0(y);
            String direction = s.nextLine();
            navezinha.setDirection(direction);

            System.out.print("Digite os comandos que a nave deverá realizar:\n");
            String comands = s.nextLine();
            explorar.setMoves(comands);

            nave.add(navezinha);
            explorando.add(explorar);

        }while (s.hasNextLine());


        for(int i = 0; i < nave.size(); i++){
            System.out.print("\n");
            System.out.print(explorando.get(i).finalPosition(nave.get(i).direction, nave.get(i).x0, nave.get(i).y0, explorando.get(i).height, explorando.get(i).width));
            System.out.print("\n");
        }


        s.close();


    }

}