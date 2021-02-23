import java.util.*;

class ShipInfo{
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

    int[] getCoordinates(char direction, int x, int y){
        int[] coordinates;
        coordinates = new int[2]; 
        if(direction == 'n'){
            coordinates[0] = x;
            coordinates[1] = y + 1;
        }else if(direction == 'e'){
            coordinates[0] = x + 1;
            coordinates[1] = y;
        }else if(direction == 's'){
            coordinates[0] = x;
            coordinates[1] = y -1;
        }else{
            coordinates[0] = x - 1;
            coordinates[1] = y;
        }

        return coordinates;
    }
}

class Exploration{
    Direction exploracao = new Direction();

    String moves;
    int height;
    int width;
    int x;
    int y;


    void setHeight(int height){
        this.height = height;
    }

    void setWidth(int width){
        this.width = width;
    }

    void setMoves(String moves){
        this.moves = moves;
    }

    int getFinalX(){
        return this.x;
    }

    int getFinalY(){
        return this.y;
    }

    String rotation(char direction){
        Direction shipDirection = new Direction();
        return shipDirection.getDirection(direction);
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
            if(moves.charAt(i) == 'l'){
                qtL++;
                difference = qtL - qtR;

                if(difference >= 0){
                    finalDirection = inicialRotation.charAt(difference % 4);
                }else if(difference < 0 && difference < 4){
                    finalDirection = inicialRotation.charAt((4 + difference) % 4);
                }else if(difference < 0 && difference > 4){
                    finalDirection = inicialRotation.charAt((-1 * difference) % 4);
                }
            }else if(moves.charAt(i) == 'r'){
                qtR++;
                difference = qtL - qtR;

                if(difference >= 0){
                    finalDirection = inicialRotation.charAt(difference % 4);
                }else if(difference < 0 && difference < 4){
                    finalDirection = inicialRotation.charAt((4 + difference) % 4);
                }else if(difference < 0 && difference > 4){
                    finalDirection = inicialRotation.charAt((-1 * difference) % 4);
                }
            }else if(moves.charAt(i) == 'm'){
                this.x = x;
                this.y = y;
                x = exploracao.getCoordinates(finalDirection, x, y)[0];
                y = exploracao.getCoordinates(finalDirection, x, y)[1];

            }
        }

        return x + " " + y + " " + Character.toUpperCase(finalDirection);

    }
}

class ErrorTreatment{

    void treatInicialPosition(int x, int y, int height, int width){
        if(height <= 0 || width <= 0){
            System.out.print("\n==========================================================================================\n");
            System.out.print("Parece que algum buraco negro destruiu as dimensões de Marte! São inválidas ou nulas :(\n");
            System.out.print("==========================================================================================\n");

        }
        else if(x > width || y > height || x < 0 || y < 0){
            System.out.print("\n===================================================================================\n");
            System.out.print("Parece que a nave nem Marte está explorando mais! Acabou saindo do perímetro :(\n");
            System.out.print("===================================================================================\n");
        }



    }

}

class Crash{
    int x;
    int y;

    void setX(int x){
        this.x = x;
    }

    void setY(int y){
        this.y = y;
    }

    void checkCrashes(ArrayList <Crash> crash){
        int flag = 0;
        for(int i = 0; i < crash.size(); i++){
            for(int j = i; j < crash.size(); j++){
                if(crash.get(i).x == crash.get(j).x && crash.get(i).y == crash.get(j).y && flag == 0){
                    flag = 1;
                    System.out.print("\n==========================================================================================\n");
                    System.out.print("A exploração em Marte não foi bem sucedida :( As naves acabaram se chocando :O\n");
                    System.out.print("==========================================================================================\n");
                }
            }
        }
    }
}

public class Code {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        ArrayList<ShipInfo> allShips = new ArrayList<ShipInfo>();
        ArrayList<Exploration> allExplorationInfo = new ArrayList<Exploration>();


        System.out.print("Digite as dimensões do terreno (separadas por espaço): \n");
        int height = s.nextInt();
        int width = s.nextInt();
        
        
        do{
            ShipInfo ship = new ShipInfo();
            Exploration explorating = new Exploration();
            explorating.setHeight(height);
            explorating.setWidth(width);
            
            System.out.print("Digite a posição inicial da nave e a direção em que está (N, E, S, W):\n");
            int x = s.nextInt();
            ship.setX0(x);
            int y = s.nextInt();
            ship.setY0(y);
            String direction = s.nextLine();
            ship.setDirection(direction.toLowerCase());

            System.out.print("Digite os comandos que a nave deverá realizar:\n");
            String commands = s.nextLine();
            explorating.setMoves(commands.toLowerCase());

            allShips.add(ship);
            allExplorationInfo.add(explorating);

        }while (s.hasNextLine());

        ArrayList<Crash> crashes = new ArrayList<Crash>();
        Crash shipsCrash = new Crash();

        for(int i = 0; i < allShips.size(); i++){
            ErrorTreatment error = new ErrorTreatment();
            Crash shipPosition = new Crash();
            System.out.print("\n");
            System.out.print("Nave " + (i + 1) + ": " + allExplorationInfo.get(i).finalPosition(allShips.get(i).direction, allShips.get(i).x0, allShips.get(i).y0, allExplorationInfo.get(i).height, allExplorationInfo.get(i).width));
            System.out.print("\n");
            error.treatInicialPosition(allExplorationInfo.get(i).getFinalX(), allExplorationInfo.get(i).getFinalY(), allExplorationInfo.get(i).height, allExplorationInfo.get(i).width);
            shipPosition.setX(allExplorationInfo.get(i).getFinalX());
            shipPosition.setY(allExplorationInfo.get(i).getFinalY());
            crashes.add(shipPosition);
        }

        shipsCrash.checkCrashes(crashes);




        s.close();


    }

}
