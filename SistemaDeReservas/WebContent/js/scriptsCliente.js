/**
 * Cadastro de clinetes
 */
$("#formularioDeCadastro").submit(function(event){
	if(capturarDadosDoForm() != null){
		var formData = JSON.stringify(capturarDadosDoForm());
		$.ajax({
			url: "ws/clientews/cadastrar/",
	        type: 'POST',
	        data: formData,
	        success: function (data) {
	        	tratarRetornoServidor(data);
	        },
			cache: false,
		    contentType: "application/json",
		    processData: true
		});
	}
	return false;
});

function capturarDadosDoForm(){
	var nome = $("#nome").val();
	var sobrenome = $("#sobrenome").val();
	var cpf = $("#cpf").val();
	var telefone = $("#telefone").val();
	var login = $("#loginC").val();
	var email = $("#email").val();
	var confirmaEmail = $("#confirmaEmail").val();
	var senha = $("#senha").val();
	var confirmaSenha = $("#confirmaSenha").val();
	var cliente;
	if(isDadosValidos(nome,sobrenome,cpf,telefone,login,email,confirmaEmail,senha,confirmaSenha)){
		cliente = {"login":login,"senha":senha,"tipo":'C',"status":'A',"cliente":{"nome":nome,"sobrenome":sobrenome,"cpf":cpf,"telefone":telefone,"email":email,"status":'A'}};
	}else{
		cliente = null;
	}
	return cliente;
}

function isDadosValidos(nome,sobrenome,cpf,telefone,login,email,confirmaEmail,senha,confirmaSenha){
	var mensagem;
//	var cpfSemFormatacao;
	var retorno = true;
	if(nome == null || nome == ""){
		mensagem += "<li>É obrigatório preencher o campo NOME</li>";
		retorno = false;
	}
	if(sobrenome == null || sobrenome == ""){
		mensagem += "<li>É obrigatório preencher o campo SOBRENOME</li>";
		retorno = false;
	}
	if(cpf == null || cpf == "" || cpf.length < 11){
		mensagem += "<li>É obrigatório preencher o campo CPF com 11 caracteres numéricos</li>";
		retorno = false;
	}
	if(login == null || login == "" || login.length < 5 ){
		mensagem += "<li>É obrigatório preencher o campo LOGIN com no mínimo 5 caracteres alfanuméricos</li>";
		retorno = false;
	}
	if(email == null || email == ""){
		mensagem += "<li>É obrigatório preencher o campo EMAIL</li>";
		retorno = false;
	}
	if((confirmaEmail == null || confirmaEmail == "") && email != confirmaEmail){
		mensagem += "<li>É obrigatório preencher o campo CONFIRMAR EMAIL com o mesmo email informado no campo EMAIL</li>";
		retorno = false;
	}	
	if(senha == null || senha == "" || senha.length < 8){
		mensagem += "<li>É obrigatório preenche o campo SENHA com no mínimo 8 caracteres alfanuméricos</li>";
		retorno = false;
	}
	if((confirmaSenha == null || confirmaSenha == "" || confirmaSenha.length < 8) && confirmaSenha != senha){
		mensagem += "<li>É obrigatório preencher o campo CONFIRMAR SENHA com a mesma senha informada no campo SENHA</li>";
		retorno = false;
	}
	return retorno;
}


function validar(dom,tipo){
	switch(tipo){
		case'num':var regex=/[A-Za-z]/g;break;
		case'text':var regex=/\d/g;break;
	}
	dom.value=dom.value.replace(regex,'');
}

function apenasNumeros(e){
	var tecla=new Number();
	if(window.event) {
		tecla = e.keyCode;
	}
	else if(e.which) {
		tecla = e.which;
	}
	else {
		return true;
	}
	if((tecla >= "97") && (tecla <= "122")){
		return false;
	}
}

function apenasLetras(e){
	var tecla=new Number();
	if(window.event) {
		tecla = e.keyCode;
	}
	else if(e.which) {
		tecla = e.which;
	}
	else {
		return true;
	}
	if((tecla >= "48") && (tecla <= "57")){
		return false;
	}
}

function tratarRetornoServidor(data){
	if(data == "sucess"){
		$('#mensagemRetornoCadastro').html("Cadastro realizado com sucesso!");
		$('#mensagemRetornoCadastro').addClass("green");
		$('#mensagemRetornoCadastro').removeClass("hiddendiv");
		setTimeout(function(){
			$('#btnAcaoFormLogin').trigger("click" );
			limparCamposFormCadastro();
			$('#mensagemRetornoCadastro').addClass("hiddendiv");
		},2000);
	}else{
		$('#mensagemRetornoCadastro').html("Houve erro ao cadastrar!");
		$('#mensagemRetornoCadastro').addClass("red");
		$('#mensagemRetornoCadastro').removeClass("hiddendiv");
	}
}

function limparCamposFormCadastro(){
	$("#nome").val("");
	$("#sobrenome").val("");
	$("#cpf").val("");
	$("#telefone").val("");
	$("#loginC").val("");
	$("#email").val("");
	$("#confirmaEmail").val("");
	$("#senha").val("");
	$("#confirmaSenha").val("");
}


