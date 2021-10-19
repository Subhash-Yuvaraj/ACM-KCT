import java.util.Scanner;
public class Box{
	public static boolean isValidInput(int m,int n,int r,int c) {
		if(m<=r*c&&n<=r*c) {
			if(m==n-1) {
				if(m%c==0)
					return false;
				else
					return true;
			}
			else if(m%c==n%c) {
				if(m/c==n/c-1)
					return true;
				return false;
			}
		}
		return false;
	}
	public static boolean ifAlreadyDrawn(int m,int n,int r,int c,int[][][] boxBorder) {
		if(isBorder(m, n, r, c)) {
			if(m==n-1) {
				if(m/c==0) {
					//only top draw
					if(boxBorder[0][(m-1)%c][0]==1)
						return true;
				}
				else {
					//bottom draw
					if(boxBorder[r-2][(m-1)%c][1]==1)
						return true;
				}
			}
			else {
				if(m%c==1) {
					//only left draw
					if(boxBorder[(m-1)/c][0][2]==1)
						return true;
				}else {
					//only right draw
					if(boxBorder[(m-1)/c][c-2][3]==1)
						return true;
				}
			}
		}
		else {
			if(m==n-1) {
				//top and bottom
				if(boxBorder[(m-1)/c][(m-1)%c][0]==1)
					return true;
				if(boxBorder[(m-1)/c-1][(m-1)%c][1]==1)
					return true;
			}
			else {
				//left and right
				if(boxBorder[(m-1)/c][(m-1)%c][2]==1)
					return true;
				if(boxBorder[(m-1)/c][(m-1)%c-1][3]==1)
					return true;
			}
		}
		return false;
	}
	public static int[][][] storeInput(int m,int n,int r,int c,int[][][] boxBorder){
		if(isBorder(m, n, r, c)) {
			if(m==n-1) {
				if(m/c==0) {
					//only top draw
					boxBorder[0][(m-1)%c][0]=1;
				}
				else {
					//bottom draw
					boxBorder[r-2][(m-1)%c][1]=1;
				}
			}
			else {
				if(m%c==1) {
					//only left draw
					boxBorder[(m-1)/c][0][2]=1;
				}else {
					//only right draw
					boxBorder[(m-1)/c][c-2][3]=1;
				}
			}
		}
		else {
			if(m==n-1) {
				//top and bottom
				boxBorder[(m-1)/c][(m-1)%c][0]=1;
				boxBorder[(m-1)/c-1][(m-1)%c][1]=1;
			}
			else {
				//left and right
				boxBorder[(m-1)/c][(m-1)%c][2]=1;
				boxBorder[(m-1)/c][(m-1)%c-1][3]=1;
			}
		}
		return boxBorder;
	}
	public static boolean isBoxCaptured(int r,int c,int[][] capturedBoxes,int[][][] boxBorderDrawn) {
		for(int i=0;i<capturedBoxes.length;i++) {
			for(int j=0;j<capturedBoxes[i].length;j++) {
				if(capturedBoxes[i][j]==0) {
					if(boxBorderDrawn[i][j][0]==1&&boxBorderDrawn[i][j][1]==1&&boxBorderDrawn[i][j][2]==1&&boxBorderDrawn[i][j][3]==1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static int[][] storeBoxCaptured(int r,int c,int[][] capturedBoxes,int[][][] boxBorderDrawn,int playerNumber){
		for(int i=0;i<capturedBoxes.length;i++) {
			for(int j=0;j<capturedBoxes[i].length;j++) {
				if(capturedBoxes[i][j]==0) {
					if(boxBorderDrawn[i][j][0]==1&&boxBorderDrawn[i][j][1]==1&&boxBorderDrawn[i][j][2]==1&&boxBorderDrawn[i][j][3]==1) {
						capturedBoxes[i][j]=playerNumber;
					}
				}
			}
		}
		return capturedBoxes;
	}
	public static boolean isGameOver(int[][] capturedBoxes) {
		for(int i=0;i<capturedBoxes.length;i++) {
			for(int j=0;j<capturedBoxes[i].length;j++) {
				if(capturedBoxes[i][j]==0)
					return false;
			}
		}
		return true;
	}
	public static int findWinner(int[][] capturedBoxes) {
		int p1=0,p2=0;
		for(int i=0;i<capturedBoxes.length;i++) {
			for(int j=0;j<capturedBoxes[i].length;j++) {
				if(capturedBoxes[i][j]==1)	p1++;
				else p2++;
			}
		}
		return p1==p2?0:p1>p2?1:2;
	}
	public static boolean isBorder(int m,int n,int r,int c) {
		int t1=(m-1)/c;
		int t2=(n-1)/c;
		int p1=m%c;
		int p2=n%c;
		if(t1==0&&t2==0)
			return true;
		if(t1==r-1&&t2==r-1)
			return true;
		if(p1==1&&p2==1)
			return true;
		if(p1==0&&p2==0)
			return true;
		return false;
	}
	public static void main(String[] args) {
		int r,c;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Rows and Columns respectively");
		r=sc.nextInt();
		c=sc.nextInt();
		int[][] capturedBoxes=new int[r-1][c-1];
		int[][][] boxBorderDrawn=new int[r-1][c-1][4];
		System.out.println("Game initialized for "+r+" X "+c);
		for(int i=0;i<r;i++) {
			for(int j=0;j<c;j++) {
				System.out.print((i*c+j+1)+"\t");
			}
			System.out.println();
			System.out.println();
		}
		int playerNumber=1;
		while(!isGameOver(capturedBoxes)) {
			System.out.println("Enter input for player "+playerNumber);
			int m,n;
			m=sc.nextInt();
			n=sc.nextInt();
			if(m>n) {
				int temp=m;
				m=n;
				n=temp;
			}
			if(isValidInput(m, n, r, c)) {
				if(ifAlreadyDrawn(m, n, r, c, boxBorderDrawn)) {
					System.out.println("The line is already drawn");
				}else {
					boxBorderDrawn=storeInput(m, n, r, c, boxBorderDrawn);
					if(isBoxCaptured(r, c, capturedBoxes, boxBorderDrawn)) {
						capturedBoxes=storeBoxCaptured(r, c, capturedBoxes, boxBorderDrawn, playerNumber);
					}else {
						playerNumber=playerNumber==1?2:1;
					}
				}
			}else {
				System.out.println("Invalid Input");
			}
			for(int i=0;i<r;i++) {
				for(int j=0;j<c;j++) {
					System.out.print(i*c+j+1);
					if(j<c-1&&i<r-1) {
							if(boxBorderDrawn[i][j][0]==1)
								System.out.print("----");
					}
					if(i==r-1&&j<c-1) {
						if(boxBorderDrawn[i-1][j][1]==1)
							System.out.print("----");
					}
					System.out.print("\t");
				}
				System.out.println();
				if(i<r-1) {
					int a;
					for(a=0;a<c-1;a++) {
						if(boxBorderDrawn[i][a][2]==1)
							System.out.print("|");
						if(capturedBoxes[i][a]!=0) {
							System.out.print("p"+capturedBoxes[i][a]);
						}
						System.out.print("\t");
					}
					if(boxBorderDrawn[i][a-1][3]==1)
						System.out.print("|");
					System.out.println();
				}
			}
		}
		int winner=findWinner(capturedBoxes);
		if(winner==0)
			System.out.println("The game is tied");
		else
			System.out.println("The winner is player "+winner);
		sc.close();
	}
}
