import React from "react";

const Dashboard = React.lazy(() => import("../pages/dashboard"));
const SignIn = React.lazy(() => import("../pages/signin"));


export const PRIVATE_ROUTES = [
    {
        path: "/dashboard",
        component: Dashboard
    },
    {
        path: '/signin',
        component: SignIn
    }
]