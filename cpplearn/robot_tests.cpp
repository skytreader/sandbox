include "gtest/gtest.h"

/**
Assuming that we have a function move_robot(string[] grid, int entry_point).
*/

TEST(corner_case, robot_motion){
    EXPECT_EQ(move_robot({}, 2), "10 step(s) to exit");
}
