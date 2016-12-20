$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	var codigoTitulo = button.data('codigo');
	var descricaoTitulo = button.data('descricao');
	
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if(!action.endsWith('/')){
		action += '/';
	}
	
	form.attr('action', action + codigoTitulo);
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o titulo <strong>' + descricaoTitulo + '</strong>?');
});

$(function(){
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({decimal:',', thousands:'.', allowZero:false});
	
	$('.js-titulo-receber').on('click', (function(event){
		event.preventDefault();
		
		var button = $(event.currentTarget);
		var link = button.attr('href');
		
		var response = $.ajax({
			url: link,
			type:'PUT'
		});
		
		response.done(function(e){
			var codigo = button.data('codigo');
			
			$('[data-role=' + codigo + ']').html('<span class="label label-success">' + e + '</span>')
			
			button.hide();
		});
		
		response.fail(function(e){
			console.log(e);
			alert('falha ao atualizar o recebimento do titulo');
		});
		
	}));
});