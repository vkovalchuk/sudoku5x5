

public final class sudoku {

static final int[] field0 = new int[25];

static final int v(int x, int y) {
    int idx = (x-1) * 5 + y - 1;
    try {
        return field0[idx];
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println();
        throw new ArrayIndexOutOfBoundsException("v[" + x + "," + y + "]: " + e.getMessage());
    }
}

static final void set_v(int x, int y, int val) {
    int idx = (x-1) * 5 + y - 1;
    field0[idx] = val;
}

static {
    set_v(2, 2, 5);
    set_v(2, 3, 4);
    set_v(4, 3, 1);
    set_v(4, 4, 3);
}

public static void main(String[] a) {
    boolean ok = solve(4);
    if (ok) {
        print_fld();
    } else {
        System.out.println("NO SOLUTION");
    }
}

static void print_fld() {
    for (int j = 5; j >= 1; j--) {
        for (int i = 1; i <= 5; i++) {
            System.out.print("|" + v(i, j));
        }
        System.out.println("|");
    }
}

static boolean solve(int num) {
    if (num == 25)
        return true;

    int coord = find_empty();
    if (coord == -1)
        return false;

    int x = coord / 5 + 1;
    int y = coord % 5 + 1;

    for (int cand = 1; cand < 6; cand++) {
       set_v(x, y, cand);
       boolean feasible = is_feasible(x, y, cand);
       if (feasible) {
          boolean try_next = solve(num+1);
          if (try_next)
              return true;
       }
    }
    set_v(x, y, 0); // backtrack
    return false;
}

static int find_empty() {
    int x, y;
    if (v(3, 3) == 0) {
       x = 3; y = 3;
    } else if (v(5, 3) == 0) {
       x = 5; y = 3;
    } else {
       boolean found = false;
       y = 1;
       outer:
       for (x = 1; x < 6; x++) {
          for (y = 1; y < 6; y++) {
              if (v(x, y) == 0) {
                 found = true;
                 break outer;
              }
          }
       }

       if (!found) {
            return -1;
       }
    }

    int idx = (x-1) * 5 + y - 1;
    return idx;
}

static boolean is_feasible(int x, int y, int cand) {
    int i;
    for (i = 1; i < x; i++)
       if (v(i, y) == cand)
          return false;
    for (i = x+1; i < 6; i++)
       if (v(i, y) == cand)
          return false;

    int j;
    for (j = 1; j < y; j++) {
       if (v(x, j) == cand)
          return false;
    }
    for (j = y+1; j < 6; j++)
       if (v(x, j) == cand)
          return false;

    boolean result;
    if ( ((x == 3) && y >= 2 && y <= 4) || (y == 3 && x >= 2 && x <= 4) ) { // blue zone
        result = zone(2, 3,   3, 2,   3, 3,   4, 3,   3, 4);
    } else if ( (x == 1 && y <= 3) || (x == 2 && y <= 2)) { // orange
        result = zone(1, 1,   1, 2,   1, 3,   2, 1,   2, 2);
    } else if ( (y == 5 && x <= 3) || (y == 4 && x <= 2)) { // yellow
        result = zone(1, 4,   1, 5,   2, 4,   2, 5,   3, 5);
    } else if ( (y == 1 && x >= 3) || (y == 2 && x >= 4)) { // green
        result = zone(3, 1,   4, 1,   4, 2,   5, 1,   5, 2);
    } else if ( (x == 4 && y >= 4) || (x == 5 && x >= 3)) { // violet
        result = zone(4, 4,   4, 5,   5, 3,   5, 4,   5, 5);
    } else 
        throw new RuntimeException("Bad ZONE: " + x + ", " + y);

    return result;
}

static boolean zone(int x1, int y1,   int x2, int y2,   int x3, int y3,   int x4, int y4,   int x5, int y5) {
    int mask1 = bit(v(x1, y1));
    int mask2 = bit(v(x2, y2));
    if ( (mask1 & mask2) != 0)
        return false;

    mask1 |= mask2;
    mask2 = bit(v(x3, y3));
    if ((mask1 & mask2) != 0)
        return false;

    mask1 |= mask2;
    mask2 = bit(v(x4, y4));
    if ((mask1 & mask2) != 0)
        return false;

    mask1 |= mask2;
    mask2 = bit(v(x5, y5));
    if ((mask1 & mask2) != 0)
        return false;

    return true;
}

static int bit(int val) {
    switch (val) {
        case 0: return 0;
        case 1: return 1;
        case 2: return 2;
        case 3: return 4;
        case 4: return 8;
        case 5: return 16;
        default: throw new RuntimeException("BAD bit: " + val);
    }
}


static void print_zones() {
    for (int j = 5; j >= 1; j--) {
        for (int i = 1; i <= 5; i++) {
            System.out.print("|" + debug_zones(i, j));
        }
        System.out.println("|");
    }
}

static String debug_zones(int x, int y) {
    if ( ((x == 3) && y >= 2 && y <= 4) || (y == 3 && x >= 2 && x <= 4) ) { // blue zone
        return "B";
    } else if ( (x == 1 && y <= 3) || (x == 2 && y <= 2)) { // orange
        return "O";
    } else if ( (y == 5 && x <= 3) || (y == 4 && x <= 2)) { // yellow
        return "Y";
    } else if ( (y == 1 && x >= 3) || (y == 2 && x >= 4)) { // green
        return "G";
    } else if ( (x == 4 && y >= 4) || (x == 5 && x >= 3)) { // violet
        return "V";
    }
    throw new RuntimeException("Bad ZONE: " + x + ", " + y);
}

} // class
