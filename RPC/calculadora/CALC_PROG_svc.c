/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "CALC_PROG.h"
#include <stdio.h>
#include <stdlib.h>
#include <rpc/pmap_clnt.h>
#include <string.h>
#include <memory.h>
#include <sys/socket.h>
#include <netinet/in.h>

#ifndef SIG_PF
#define SIG_PF void(*)(int)
#endif

static void
calc_prog_1(struct svc_req *rqstp, register SVCXPRT *transp)
{
	union {
		doublepair sumar_1_arg;
		doublepair multiplicar_1_arg;
		doublepair dividir_1_arg;
		doublepair porcentaje_1_arg;
		double raizcuadrada_1_arg;
		doublepair potencia_1_arg;
		double reciproco_1_arg;
	} argument;
	char *result;
	xdrproc_t _xdr_argument, _xdr_result;
	char *(*local)(char *, struct svc_req *);

	switch (rqstp->rq_proc) {
	case NULLPROC:
		(void) svc_sendreply (transp, (xdrproc_t) xdr_void, (char *)NULL);
		return;

	case sumar:
		_xdr_argument = (xdrproc_t) xdr_doublepair;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) sumar_1_svc;
		break;

	case multiplicar:
		_xdr_argument = (xdrproc_t) xdr_doublepair;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) multiplicar_1_svc;
		break;

	case dividir:
		_xdr_argument = (xdrproc_t) xdr_doublepair;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) dividir_1_svc;
		break;

	case porcentaje:
		_xdr_argument = (xdrproc_t) xdr_doublepair;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) porcentaje_1_svc;
		break;

	case raizCuadrada:
		_xdr_argument = (xdrproc_t) xdr_double;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) raizcuadrada_1_svc;
		break;

	case potencia:
		_xdr_argument = (xdrproc_t) xdr_doublepair;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) potencia_1_svc;
		break;

	case reciproco:
		_xdr_argument = (xdrproc_t) xdr_double;
		_xdr_result = (xdrproc_t) xdr_double;
		local = (char *(*)(char *, struct svc_req *)) reciproco_1_svc;
		break;

	default:
		svcerr_noproc (transp);
		return;
	}
	memset ((char *)&argument, 0, sizeof (argument));
	if (!svc_getargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		svcerr_decode (transp);
		return;
	}
	result = (*local)((char *)&argument, rqstp);
	if (result != NULL && !svc_sendreply(transp, (xdrproc_t) _xdr_result, result)) {
		svcerr_systemerr (transp);
	}
	if (!svc_freeargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		fprintf (stderr, "%s", "unable to free arguments");
		exit (1);
	}
	return;
}

int
main (int argc, char **argv)
{
	register SVCXPRT *transp;

	pmap_unset (CALC_PROG, CALC_VERS);

	transp = svcudp_create(RPC_ANYSOCK);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create udp service.");
		exit(1);
	}
	if (!svc_register(transp, CALC_PROG, CALC_VERS, calc_prog_1, IPPROTO_UDP)) {
		fprintf (stderr, "%s", "unable to register (CALC_PROG, CALC_VERS, udp).");
		exit(1);
	}

	transp = svctcp_create(RPC_ANYSOCK, 0, 0);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create tcp service.");
		exit(1);
	}
	if (!svc_register(transp, CALC_PROG, CALC_VERS, calc_prog_1, IPPROTO_TCP)) {
		fprintf (stderr, "%s", "unable to register (CALC_PROG, CALC_VERS, tcp).");
		exit(1);
	}

	svc_run ();
	fprintf (stderr, "%s", "svc_run returned");
	exit (1);
	/* NOTREACHED */
}
