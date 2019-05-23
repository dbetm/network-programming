#include <iostream>
#include <thread>
#include <chrono>

using namespace std;

class Contador {
    public:
        void operator()(int inicio) {
            while(true) {
                this_thread::sleep_for(chrono::milliseconds(3000));
                cout << sumar(inicio, inicio+1) << endl;
                inicio++;
                if(inicio == 10) break;
            }
        }
        int sumar(int a, int b) {
            return a + b;
        }
};

int main() {
    cout << "Hilos corriendo" << endl;
    thread t1(Contador(), 0);
    t1.join();
    return 0;
}
