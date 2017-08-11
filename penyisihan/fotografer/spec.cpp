#include <tcframe/spec.hpp>
#include <bits/stdc++.h>

using namespace tcframe;
using namespace std;

class ProblemSpec : public BaseProblemSpec {
protected:
    int A, B, K;
    string res;

    void InputFormat() {
        LINE(A, B, K);
    }

    void OutputFormat() {
        LINE(res); // "mustahil" if there is no solution
    }

    void StyleConfig() {
        CustomScorer();
    }

    void Constraints() {
        CONS(1 <= B && B <= 100000);
        CONS(1 <= K && K <= B);
    }

    void Subtask1() {
        CONS(0 <= A <= 1);
    }

    void Subtask2() {
        CONS(0 <= A <= 100000);
    }
};

class TestSpec : public BaseTestSpec<ProblemSpec> {
protected:
    void TestGroup1() {
        Subtasks({1, 2});

    }

    void TestGroup2() {
        Subtasks({2});

    }
};