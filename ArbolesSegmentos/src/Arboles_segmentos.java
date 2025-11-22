public class Arboles_segmentos {

    static class Node {
        double sum;
        double sumSq;
        int count;

        Node(double sum, double sumSq, int count) {
            this.sum = sum;
            this.sumSq = sumSq;
            this.count = count;
        }
    }

    Node[] tree;
    int n;

    public Arboles_segmentos(double[] arr) {
        n = arr.length;
        tree = new Node[4 * n];
        build(1, 0, n - 1, arr);
    }

    private Node merge(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;
        return new Node(a.sum + b.sum, a.sumSq + b.sumSq, a.count + b.count);
    }

    private void build(int idx, int l, int r, double[] arr) {
        if (l == r) {
            tree[idx] = new Node(arr[l], arr[l] * arr[l], 1);
            return;
        }
        int mid = (l + r) / 2;
        build(idx * 2, l, mid, arr);
        build(idx * 2 + 1, mid + 1, r, arr);
        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private void update(int idx, int l, int r, int pos, double val) {
        if (l == r) {
            tree[idx] = new Node(val, val * val, 1);
            return;
        }
        int mid = (l + r) / 2;
        if (pos <= mid) update(idx * 2, l, mid, pos, val);
        else update(idx * 2 + 1, mid + 1, r, pos, val);
        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    public void update(int pos, double val) {
        update(1, 0, n - 1, pos, val);
    }
    
    private Node query(int idx, int l, int r, int ql, int qr) {
        if (qr < l || ql > r) return null;
        if (ql <= l && r <= qr) return tree[idx];
        int mid = (l + r) / 2;
        Node left = query(idx * 2, l, mid, ql, qr);
        Node right = query(idx * 2 + 1, mid + 1, r, ql, qr);
        return merge(left, right);
    }

    public double variance(int l, int r) {
        Node res = query(1, 0, n - 1, l, r);
        if (res == null || res.count == 0) return 0;
        double mean = res.sum / res.count;
        return (res.sumSq / res.count) - (mean * mean);
    }

    public static void main(String[] args) {
        double[] arr = {5, 5, 10, 5, 5};
        Arboles_segmentos st = new Arboles_segmentos(arr);

        System.out.println(st.variance(0, 4));
        st.update(2, 5);
        System.out.println(st.variance(0, 4));
    }
}

