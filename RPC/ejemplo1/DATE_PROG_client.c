/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "DATE_PROG.h"


void date_prog_1(char *host, char *res) {
	CLIENT *clnt;
	long  *result_1;
	char *bin_date_1_arg;
	char * *result_2;
	long  str_date_1_arg;

#ifndef	DEBUG
	clnt = clnt_create (host, DATE_PROG, DATE_VERS, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

    if(strcmp(res, "null") == 0) {
        result_1 = bin_date_1((void*)&bin_date_1_arg, clnt);
    	if (result_1 == (long *) NULL) {
    		clnt_perror (clnt, "call failed");
    	}
        long long int timestamp = (*result_1) * 10000;
        printf("%lld\n", timestamp);
    }
    else {
        str_date_1_arg = atoi(res);
    	result_2 = str_date_1(&str_date_1_arg, clnt);
    	if (result_2 == (char **) NULL) {
    		clnt_perror (clnt, "call failed");
    	}
        printf("%s\n", *result_2);
    }

#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int main (int argc, char *argv[]) {
	char *host;
	if (argc < 3) {
		printf ("usage: %s server_host opt[null | #]\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	date_prog_1 (host, argv[2]);

    exit (0);
}
