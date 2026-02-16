package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, curentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }
    public void instantiateNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col =0;
        int row = 0;

        while(col< gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row] = new Node(col,row);
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes(){

        int col =0;
        int row = 0;

        while(col< gp.maxWorldCol && row < gp.maxWorldRow){
            //RESET OPEN, CHECKED, SOLID STATE
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        //RESET OTHER SETTINGS
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();
        //SET START GOAL NODE
        startNode = node[startCol][startRow];
        curentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(curentNode);

        int col =0;
        int row = 0;

        while(col< gp.maxWorldCol && row < gp.maxWorldRow){
            //SET SOLID NODE
            //CHECK TILES
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision == true){
                node[col][row].solid = true;
            }
            //CHEAK INTERACTIVE TILES
            for(int i = 0;i< gp.iTile[gp.currentMap].length; i++){
                if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true){
                    int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }
            //SET COST
            getCost(node[col][row]);

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void getCost(Node node){
        // G COST
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        // H COST
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // F COST
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(goalReached == false && step < 500){
             int col = curentNode.col;
             int row = curentNode.row;
             //CHECK THE CURRENT NODE
            curentNode.checked = true;
            openList.remove(curentNode);

            //OPEN THE UP NODE
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }
            //OPEN THE LEFT NODE
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }
            //OPEN THE DOWN NODE
            if(row + 1 < gp.maxWorldRow){
                openNode(node[col][row+1]);
            }
            //OPEN THE RIGHT NODE
            if(col + 1 < gp.maxWorldCol){
                openNode(node[col+1][row]);
            }
            //FIND THE BEST NODE
            int bestNodeIndex =0;
            int bestNodefCost = 999;

            for(int i = 0; i < openList.size(); i++){
                //CHECK IF THIS NODE  F COST BETTER
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //IF F COST IS EQUAL CHECK THE G COST
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            //IF IS NO NODE IN THE OPENLIST END THE LOOP
            if(openList.size() == 0){
                break;
            }

            curentNode = openList.get(bestNodeIndex);
            if(curentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node.open = true;
            node.parent = curentNode;
            openList.add(node);
        }
    }
    public void trackThePath(){
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
